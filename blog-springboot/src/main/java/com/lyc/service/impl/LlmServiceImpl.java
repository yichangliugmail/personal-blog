package com.lyc.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.lyc.common.Result;
import com.lyc.config.properties.XfXhProperties;
import com.lyc.consumer.XfxhSocketListener;
import com.lyc.model.dto.MsgDTO;
import com.lyc.model.dto.RequestDTO;
import com.lyc.service.LlmService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 大模型服务层实现
 * @Author liuYichang
 * @Date 2024/6/5 11:15
 **/
@Slf4j
@Service
public class LlmServiceImpl implements LlmService {

    @Autowired
    private XfXhProperties xfXhProperties;

    /**
     * 发送问题到讯飞星火
     *
     * @param question 问题文本
     * @return 回答
     */
    @Override
    public Result<String> sendQuestionXfxh(String question) throws Exception {
        // 条件判断,获取令牌
        if (StringUtils.isBlank(question)) {
            return Result.fail("问题不能为空");
        }
        // 构建鉴权url
        String authUrl = getAuthUrl(xfXhProperties.getHostUrl(), xfXhProperties.getApiKey(), xfXhProperties.getApiSecret());
        OkHttpClient client = new OkHttpClient.Builder().build();
        log.info("authUrl:{}",authUrl);
        String url = authUrl.replace("http://", "ws://").replace("https://", "wss://");
        log.info("url:{}",url);
        Request request = new Request.Builder().url(url).build();
        XfxhSocketListener listener = new XfxhSocketListener();
        WebSocket webSocket = client.newWebSocket(request, listener);
        // 构造请求
        String requestJson = getRequestJson(question);
        log.info(">>>请求参数：{}",requestJson);
        webSocket.send(requestJson);
        try {
            int count = 1;
            while (count * 200/1000 < xfXhProperties.getMaxResponseTime()) {
                if (listener.wsCloseFlag) {
                    webSocket.close(1000, "close");
                    return Result.success(listener.answer.toString());
                }
                Thread.sleep(200);
                count++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.fail("请求超时" + listener.answer.toString());

    }


    private String getRequestJson(String question){
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setHeader(new RequestDTO.HeaderDTO(xfXhProperties.getAppId(), UUID.randomUUID().toString()));
        requestDTO.setParameter(new RequestDTO.ParameterDTO(new RequestDTO.ParameterDTO.ChatDTO(
                xfXhProperties.getDomain(), xfXhProperties.getTemperature(), xfXhProperties.getMaxTokens())));
        MsgDTO msgDTO = MsgDTO.createUserMsg(question);
        requestDTO.setPayload(new RequestDTO.PayloadDTO(new RequestDTO.PayloadDTO.MessageDTO(Collections.singletonList(msgDTO))));
        return JSONObject.toJSONString(requestDTO);

    }

    public String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";
        // System.err.println(preStr);
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        // System.err.println(sha);
        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();

        // System.err.println(httpUrl.toString());
        return httpUrl.toString();
    }


}

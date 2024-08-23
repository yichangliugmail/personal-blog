package com.lyc.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.lyc.model.common.Result;
import com.lyc.config.properties.XfXhProperties;
import com.lyc.consumer.XfxhSocketListener;
import com.lyc.model.dto.MsgDTO;
import com.lyc.model.dto.RequestDTO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description 大模型服务层实现
 * @Author liuYichang
 * @Date 2024/6/5 11:15
 **/
@Slf4j
@Service
public class LlmService {

    @Autowired
    private XfXhProperties xfXhProperties;

    /**
     * 这个用来保存用户与服务器之间的连接信息
     */
    public static final Map<Long, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    //历史对话，需要按照user,assistant
    List<Map<String,String>> messages = new ArrayList<>();

    /**
     * 发送问题到讯飞星火
     *
     * @param question 问题文本
     * @return 回答
     */
    public Result<String> sendQuestionXfxh(String question) throws Exception {
        // 条件判断,获取令牌
        if (StringUtils.isBlank(question)) {
            return Result.fail("问题不能为空");
        }
        // 构建鉴权url
        String authUrl = getAuthUrl(xfXhProperties.getHostUrl(), xfXhProperties.getApiKey(), xfXhProperties.getApiSecret());
        OkHttpClient client = new OkHttpClient.Builder().build();
        log.info("authUrl:{}", authUrl);
        String url = authUrl.replace("http://", "ws://").replace("https://", "wss://");
        log.info("url:{}", url);
        Request request = new Request.Builder().url(url).build();
        XfxhSocketListener listener = new XfxhSocketListener();
        WebSocket webSocket = client.newWebSocket(request, listener);
        // 构造请求
        String requestJson = getRequestJson(question);
        log.info(">>>请求参数：{}", requestJson);
        webSocket.send(requestJson);
        try {
            int count = 1;
            while (count * 200 / 1000 < xfXhProperties.getMaxResponseTime()) {
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

    public SseEmitter createConnect(Long clientId) {
        //已经连接过，直接返回连接
        if (sseEmitterMap.containsKey(clientId)) {
            log.info("返回已有sse连接，当前客户端：{}", clientId);
            return sseEmitterMap.get(clientId);
        }

        try {
            // 设置超时时间，0表示不过期。默认30秒
            SseEmitter sseEmitter = new SseEmitter(300*1000L);

            // 注册回调
//            sseEmitter.onCompletion(completionCallBack(clientId));
//            sseEmitter.onTimeout(timeoutCallBack(clientId));
            sseEmitterMap.put(clientId, sseEmitter);
            log.info("创建sse连接完成，当前客户端：{}", clientId);
            return sseEmitter;
        } catch (Exception e) {
            log.info("创建sse连接异常，当前客户端：{}", clientId);
        }
        return null;
    }

    /**
     * 用来异步发送消息
     */
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * SSE方式向前端发送消息
     *
     * @param clientId
     * @param question
     */
    public void chatStream(Long clientId, String question) {
        HashMap<String, String> user = new HashMap<>();
        user.put("role","user");
        user.put("content",question);
        messages.add(user);
        //异步发送消息
        executorService.execute(() -> {
            log.info("当前连接数：{}", sseEmitterMap.size());
            SseEmitter sseEmitter = sseEmitterMap.get(clientId);
            if (sseEmitter == null) {
                sseEmitter = createConnect(clientId);
            }

            String authUrl;
            try {
                authUrl = getAuthUrl(xfXhProperties.getHostUrl(), xfXhProperties.getApiKey(), xfXhProperties.getApiSecret());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            String url = authUrl.replace("http://", "ws://").replace("https://", "wss://");
            log.info("请求url:{}", url);
            Request request = new Request.Builder().url(url).build();

            //将回复的内容添加到消息中
            HashMap<String, String> assistant = new HashMap<>();
            assistant.put("role", "assistant");
            assistant.put("content", "");

            OkHttpClient client = new OkHttpClient.Builder().build();
            // 发起异步请求
            try {
                Response response = client.newCall(request).execute();
                // 检查响应是否成功
                if (response.isSuccessful()) {
                    // 获取响应流
                    try (ResponseBody responseBody = response.body()) {
                        if (responseBody != null) {
                            InputStream inputStream = responseBody.byteStream();
                            // 以流的方式处理响应内容，输出到控制台 这里的数组大小一定不能太小，否则会导致接收中文字符的时候产生乱码
                            byte[] buffer = new byte[2048];
                            int bytesRead;
                            StringBuilder temp = new StringBuilder();
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                //消息分割采用标识符 \n\n 来分割 并且需要从后向前找\n\n，因为每条消息分割点的最后才是\n\n
                                temp.append(new String(buffer, 0, bytesRead));
                                String result = "";
                                if (temp.lastIndexOf("\n\n") != -1) {
                                    //从6开始 因为有 data: 这个前缀 占了6个字符所以 0 + 6 = 6
                                    JSONObject jsonObject = JSON.parseObject(temp.substring(6, temp.lastIndexOf("\n\n")));
                                    temp = new StringBuilder(temp.substring(temp.lastIndexOf("\n\n") + 2));
                                    if (jsonObject != null && jsonObject.getString("result") != null) {
                                        result = jsonObject.getString("result");
                                    }
                                }
                                if (!result.equals("")) {
                                    //SSE协议默认是以两个\n换行符为结束标志 需要在进行一次转义才能成功发送给前端
                                    sseEmitter.send(SseEmitter.event().data(result.replace("\n", "\\n")));
                                    //将结果汇总起来
                                    assistant.put("content", assistant.get("content") + result);
                                }
                            }
                            messages.add(assistant);
                        }
                    }
                } else {
                    log.error("请求出错，响应结果：{}", response);
                }

            } catch (IOException e) {
                log.error("流式请求出错,断开与{}的连接", clientId);
                //移除当前的连接
                sseEmitterMap.remove(clientId);
                //移除本次对话的内容
                messages.remove(user);
            }
        });
    }


    private String getRequestJson(String question) {
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

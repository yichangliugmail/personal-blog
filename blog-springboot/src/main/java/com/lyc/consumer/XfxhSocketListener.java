package com.lyc.consumer;

import com.alibaba.fastjson2.JSONObject;
import com.lyc.model.dto.MsgDTO;
import com.lyc.model.dto.ResponseDTO;
import com.lyc.service.LlmService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * @Description 星火websocket监听器
 * @Author liuYichang
 * @Date 2024/6/5 16:26
 **/
@Slf4j
public class XfxhSocketListener extends WebSocketListener {

    public XfxhSocketListener(){}

    public StringBuilder answer = new StringBuilder();

    public boolean wsCloseFlag = false;

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        log.info(">>>onOpen 与大模型建立连接");

    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        log.info(">>>onMessage 接口响应{}",text);
        super.onMessage(webSocket, text);
        ResponseDTO response = JSONObject.parseObject(text, ResponseDTO.class);
        // 如果响应数据中的 header 的 code 值不为 0，则表示响应错误
        if (response.getHeader().getCode() != 0) {
            // 设置回答
            this.answer = new StringBuilder("大模型响应错误，请稍后再试");
            // 关闭连接标识
            wsCloseFlag = true;
            return;
        }
        // 将回答进行拼接
        for (MsgDTO msgDTO : response.getPayload().getChoices().getText()) {
            this.answer.append(msgDTO.getContent());
        }
        // 对最后一个文本结果进行处理
        if (2 == response.getHeader().getStatus()) {
            wsCloseFlag = true;
        }

    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, Response response) {
        log.info(">>>onFailure 无法建立连接，原因：{},响应：{}", t.getMessage(),response);
        super.onFailure(webSocket, t, response);
    }

}

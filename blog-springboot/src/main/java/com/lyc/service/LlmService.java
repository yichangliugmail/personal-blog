package com.lyc.service;

import com.lyc.common.Result;

/**
 * @Description 大模型服务层
 * @Author liuYichang
 * @Date 2024/6/5 11:14
 **/
public interface LlmService {
    Result<String> sendQuestionXfxh(String question) throws Exception;
}

package com.lyc.controller;

import com.lyc.common.Result;
import com.lyc.service.LlmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 大模型控制器
 * @create 2023-09-20 1:42
 */
@RestController
@RequestMapping("/llm")
@Slf4j
public class LlmController {

    @Autowired
    private LlmService llmService;

    /**
     * 发送问题
     *
     * @param question 问题
     * @return 星火大模型的回答
     */
    @GetMapping("/xfxh/sendQuestion")
    public Result<String> sendQuestion(String question) throws Exception {
        return llmService.sendQuestionXfxh(question);
    }
}

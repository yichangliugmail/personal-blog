package com.lyc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.lyc.service.XxlJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description XxlJob任务调度
 * @Author liuYichang
 * @Date 2024/4/30 17:16
 **/
@Api(tags = "XxlJob任务调度")
@RestController
public class XxlJobController {

    @Autowired
    private XxlJobService xxlJobService;

    /**
     * 获取任务调度列表
     *
     * @return
     */
    @SaCheckLogin
    @ApiOperation(value = "获取任务调度列表")
    @GetMapping("/job/pageList")
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        int jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author) {

        return xxlJobService.pageList(start, length, jobGroup, triggerStatus, jobDesc, executorHandler, author);
    }
}

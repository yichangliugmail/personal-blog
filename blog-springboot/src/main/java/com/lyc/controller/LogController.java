package com.lyc.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.lyc.model.common.PageResult;
import com.lyc.model.common.Result;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.ExceptionLog;
import com.lyc.service.ExceptionLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lyc.constant.enums.StatusCodeEnum.*;

/**
 * @author liuYichang
 * @description
 * @date 2023/4/30 15:27
 */

@Api(value = "日志控制器",tags = "日志模块")
@RestController
public class LogController {

    @Autowired
    private ExceptionLogService exceptionLogService;

    @ApiOperation("获取异常日志")
    @SaCheckPermission("log:exception:list")
    @GetMapping("/admin/exception/list")
    public Result<PageResult<ExceptionLog>> exceptionLogs(ConditionDTO condition){
        PageResult<ExceptionLog> exceptionLogList= exceptionLogService.getLogList(condition);

        return Result.success(exceptionLogList,SUCCESS.getCode(),SUCCESS.getMsg());
    }
}

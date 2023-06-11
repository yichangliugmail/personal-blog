package com.lyc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.lyc.common.PageResult;
import com.lyc.common.Result;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.vo.TaskBackVO;
import com.lyc.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务管理
 *
 * @author LiuYiChang
 * @date 2023/6/11 16:04
 */
@Api(tags = "定时任务模块")
@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @ApiOperation("获取定时任务集合")
    @SaCheckLogin
    @GetMapping("/admin/task/list")
    public Result<PageResult<TaskBackVO>> getTaskBackVOList(ConditionDTO conditionDTO){
        PageResult<TaskBackVO> taskList=taskService.getTaskBackVOList(conditionDTO);
        return Result.success(taskList);
    }
}

package com.lyc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.lyc.model.common.PageResult;
import com.lyc.model.common.Result;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.dto.TaskDTO;
import com.lyc.model.vo.TaskBackVO;
import com.lyc.model.vo.TaskStatusVO;
import com.lyc.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @ApiOperation("修改定时任务状态")
    @SaCheckPermission("monitor:task:status")
    @PutMapping("/admin/task/changeStatus")
    public Result<Object> changeStatus(@RequestBody TaskStatusVO taskStatusVO){
        taskService.changeStatus(taskStatusVO);
        return Result.success(null);
    }

    @ApiOperation("修改定时任务")
    @SaCheckPermission("monitor:task:update")
    @PutMapping("/admin/task/update")
    public Result<Object> updateTask(@RequestBody TaskDTO taskDTO){
        taskService.updateTask(taskDTO);
        return Result.success(null);
    }
}

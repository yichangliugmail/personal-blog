package com.lyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.model.po.TaskLog;
import com.lyc.service.TaskLogService;
import com.lyc.mapper.TaskLogMapper;
import org.springframework.stereotype.Service;

/**
* @author 蜡笔
* @description 针对表【t_task_log】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class TaskLogServiceImpl extends ServiceImpl<TaskLogMapper, TaskLog>
    implements TaskLogService{

}





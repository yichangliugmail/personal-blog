package com.lyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.model.po.Task;
import com.lyc.service.TaskService;
import com.lyc.mapper.TaskMapper;
import org.springframework.stereotype.Service;

/**
* @author 蜡笔
* @description 针对表【t_task】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task>
    implements TaskService{

}





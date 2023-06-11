package com.lyc.service;

import com.lyc.common.PageResult;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Task;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.model.vo.TaskBackVO;

/**
* @author 蜡笔
* @description 针对表【t_task】的数据库操作Service
* @createDate 2023-04-24 21:06:36
*/
public interface TaskService extends IService<Task> {

    PageResult<TaskBackVO> getTaskBackVOList(ConditionDTO conditionDTO);
}

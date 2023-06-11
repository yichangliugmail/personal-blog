package com.lyc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.common.PageResult;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Task;
import com.lyc.model.vo.TaskBackVO;
import com.lyc.service.TaskService;
import com.lyc.mapper.TaskMapper;
import com.lyc.quartz.utils.CronUtils;
import com.lyc.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
* @author 蜡笔
* @description 针对表【t_task】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService{

    @Resource
    private TaskMapper taskMapper;

    @Override
    public PageResult<TaskBackVO> getTaskBackVOList(ConditionDTO condition) {
        //条件查询任务数量
        Long count = taskMapper.selectCount(new LambdaQueryWrapper<Task>()
                .like(StringUtils.hasText(condition.getKeyword()),Task::getTaskName, condition.getKeyword())
                .like(StringUtils.hasText(condition.getTaskGroup()),Task::getTaskGroup, condition.getTaskGroup())
                .eq(Objects.nonNull(condition.getStatus()),Task::getStatus, condition.getStatus()));

        if (count==0){
            return new PageResult<>();
        }

        List<TaskBackVO> taskBackVOList=taskMapper.selectTaskBackVOList(PageUtils.getLimit(),PageUtils.getSize(),condition);
        //设置下一次执行时间
        taskBackVOList.forEach(item ->{
            if(StringUtils.hasText(item.getCronExpression())){
                Date nextValidTime= CronUtils.getNextExecution(item.getCronExpression());
                item.setNextValidTime(nextValidTime);
            }else {
                item.setNextValidTime(null);
            }
        });
        return new PageResult<>(taskBackVOList, count);
    }
}





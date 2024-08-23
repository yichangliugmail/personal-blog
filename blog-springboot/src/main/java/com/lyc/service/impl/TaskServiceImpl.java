package com.lyc.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.model.common.PageResult;
import com.lyc.handler.ServiceException;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.dto.TaskDTO;
import com.lyc.model.po.Task;
import com.lyc.model.vo.TaskBackVO;
import com.lyc.model.vo.TaskStatusVO;
import com.lyc.quartz.utils.ScheduleUtils;
import com.lyc.service.TaskService;
import com.lyc.mapper.TaskMapper;
import com.lyc.quartz.utils.CronUtils;
import com.lyc.utils.PageUtils;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private Scheduler scheduler;

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

    @Override
    public void changeStatus(TaskStatusVO taskStatusVO) {
        Integer taskId = taskStatusVO.getId();
        Integer status = taskStatusVO.getStatus();
        if(status==0){
            taskMapper.updateStatusById(taskId,1);
        }else {
            taskMapper.updateStatusById(taskId,0);
        }

    }

    @Override
    public void updateTask(TaskDTO taskDTO) {
        //判断Cron表达式是否合法
        Assert.isTrue(CronUtils.isValid(taskDTO.getCronExpression()),"Cron表达式无效！");

        Task task = taskMapper.selectById(taskDTO.getId());
        BeanUtils.copyProperties(taskDTO,task);
        int count = taskMapper.updateById(task);
        if(count>0){
            try{
                updateSchedulerJob(task,taskDTO.getTaskGroup());
            }catch (SchedulerException e){
                throw new ServiceException("更新定时任务失败");
            }
        }

    }

    /**
     * 更新任务
     *
     * @param task      任务对象
     * @param taskGroup 任务组名
     */
    public void updateSchedulerJob(Task task, String taskGroup) throws SchedulerException {
        Integer taskId = task.getId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(taskId, taskGroup);
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, task);
    }
}





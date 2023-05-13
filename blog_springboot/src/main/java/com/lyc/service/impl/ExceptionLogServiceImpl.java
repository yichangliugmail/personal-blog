package com.lyc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.common.PageResult;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.ExceptionLog;
import com.lyc.service.ExceptionLogService;
import com.lyc.mapper.ExceptionLogMapper;
import com.lyc.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_exception_log】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class ExceptionLogServiceImpl extends ServiceImpl<ExceptionLogMapper, ExceptionLog> implements ExceptionLogService{

    @Resource
    private ExceptionLogMapper exceptionLogMapper;

    @Override
    public PageResult<ExceptionLog> getLogList(ConditionDTO condition) {
        //异常日志数量
        Long count=exceptionLogMapper.selectCount(new LambdaQueryWrapper<ExceptionLog>()
                .like(StringUtils.hasText(condition.getOptModule()), ExceptionLog::getModule, condition.getOptModule())
                .or()
                .like(StringUtils.hasText(condition.getKeyword()),ExceptionLog::getDescription,condition.getKeyword())
        );
        //没有日志
        if (count==0){
            return new PageResult<>();
        }
        //条件查询
        List<ExceptionLog> logList = exceptionLogMapper.selectLogList(PageUtils.getLimit(),PageUtils.getSize(),condition);

        return new PageResult<>(logList,count);
    }

    @Override
    public void saveExceptionLog(ExceptionLog exceptionLog) {
        exceptionLogMapper.insert(exceptionLog);
    }
}





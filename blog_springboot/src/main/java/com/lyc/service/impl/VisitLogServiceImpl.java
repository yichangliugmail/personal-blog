package com.lyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.handler.ServiceException;
import com.lyc.model.po.VisitLog;
import com.lyc.service.VisitLogService;
import com.lyc.mapper.VisitLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 蜡笔
* @description 针对表【t_visit_log】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class VisitLogServiceImpl extends ServiceImpl<VisitLogMapper, VisitLog> implements VisitLogService{
    @Resource
    private VisitLogMapper visitLogMapper;

    @Override
    public void saveVisitLog(VisitLog visitLog) {
        int count = visitLogMapper.insert(visitLog);
        if(count==0){
            throw new ServiceException("访问日志插入失败！");
        }
    }
}





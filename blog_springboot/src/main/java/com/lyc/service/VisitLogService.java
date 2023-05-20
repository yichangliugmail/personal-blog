package com.lyc.service;

import com.lyc.model.po.VisitLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 蜡笔
* @description 针对表【t_visit_log】的数据库操作Service
* @createDate 2023-04-24 21:06:36
*/
public interface VisitLogService extends IService<VisitLog> {

    /**
     * 保存异常日志数据
     */
    void saveVisitLog(VisitLog visitLog);
}

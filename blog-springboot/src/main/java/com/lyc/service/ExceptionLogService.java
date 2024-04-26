package com.lyc.service;

import com.lyc.common.PageResult;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.ExceptionLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 蜡笔
* @description 针对表【t_exception_log】的数据库操作Service
* @createDate 2023-04-24 21:06:36
*/
public interface ExceptionLogService extends IService<ExceptionLog> {

    /**
     * 获取异常日志信息集合
     * @param condition 条件
     * @return rr
     */
    PageResult<ExceptionLog> getLogList(ConditionDTO condition);

    /**
     * 保存异常日志信息
     * @param exceptionLog 异常日志信息
     */
    void saveExceptionLog(ExceptionLog exceptionLog);
}

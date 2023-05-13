package com.lyc.manager.factory;

import com.lyc.config.SpringConfig;
import com.lyc.model.po.ExceptionLog;
import com.lyc.service.ExceptionLogService;

import java.util.TimerTask;

/**
 * @author 刘怡畅
 * @description 异步任务工厂
 * @date 2023/5/2 10:48
 */
public class AsyncFactory {

    /**
     * 创建一个定时任务，保存异常日志
     * @param exceptionLog 异常日志信息
     */
    public static TimerTask recordException(ExceptionLog exceptionLog) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringConfig.getBean(ExceptionLogService.class).saveExceptionLog(exceptionLog);
            }
        };
    }


}

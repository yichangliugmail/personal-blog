package com.lyc.service.jobhandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * @Description 博客定时任务
 * @Author liuYichang
 * @Date 2024/4/28 15:48
 **/

@Component
public class BlogXxlJob {

    /**
     * TODO 清除访问日志
     */
    @XxlJob("clearVisitLogJobHandler")
    public void clearVisitLogJobHandler() {

    }


}

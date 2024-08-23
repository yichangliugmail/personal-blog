package com.lyc.manager;

import com.lyc.config.SpringConfig;
import com.lyc.utils.ThreadUtils;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author liuYichang
 * @description 异步任务管理器，用于管理线程池和异步任务
 * @date 2023/5/2 9:45
 */
public class AsyncManager {
    private AsyncManager(){}

    //饿汉单例，避免线程池的过创建
    private static final AsyncManager INSTANCE=new AsyncManager();

    //异步任务调度线程池
    private final ScheduledExecutorService executor= SpringConfig.getBean("scheduledExecutorService");

    /**
     * 获取单例对象
     * @return INSTANCE
     */
    public static AsyncManager getInstance(){
        return INSTANCE;
    }

    /**
     * 执行定时任务
     */
    public void execute(TimerTask task){
        executor.schedule(task,10, TimeUnit.MILLISECONDS);
    }

    /**
     * 停止任务
     */
    public void shutdown(){
        ThreadUtils.shutdownAndWaitTermination(executor);
    }

}

package com.lyc.config;

import com.lyc.config.properties.ThreadPoolProperties;
import com.lyc.utils.ThreadUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author liuYichang
 * @description 线程池配置
 * @date 2023/5/2 16:33
 */

@Configuration
public class ThreadPoolConfig {

    @Autowired
    private ThreadPoolProperties threadPoolProperties;

    /**
     * 创建线程池
     * @return 线程池
     */
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程池大小
        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        // 最大可创建的线程数
        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        // 等待队列最大长度
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        // 线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        // 线程池对拒绝任务(无线程可用)的处理策略，交给主线程
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    /**
     * 执行周期性或定时任务
     */
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService() {
        //核心线程池大小
        int corePoolSize = threadPoolProperties.getCorePoolSize();
        //创建线程工厂对象，该对象用来创建新线程
        BasicThreadFactory threadFactory = new BasicThreadFactory
                .Builder()
                .namingPattern("schedule-pool-%d")
                .daemon(true)
                .build();

        //第三个参数是指当线程池中所有线程都在工作，且线程队列已满，任务将交给主线程
        ScheduledThreadPoolExecutor executor= new ScheduledThreadPoolExecutor(corePoolSize, threadFactory, new ThreadPoolExecutor.CallerRunsPolicy()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                ThreadUtils.printException(r, t);
            }
        };

        return  executor;
    }

}

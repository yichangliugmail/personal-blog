package com.lyc.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义配置属性，线程池
 *
 * @author 刘怡畅
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "thread.pool")
public class ThreadPoolProperties {

    /**
     * 核心线程数
     */
    private int corePoolSize;

    /**
     * 最大线程数
     */
    private int maxPoolSize;

    /**
     * 线程队列长度
     */
    private int queueCapacity;

    /**
     * 空闲线程存活时间
     */
    private int keepAliveSeconds;

}

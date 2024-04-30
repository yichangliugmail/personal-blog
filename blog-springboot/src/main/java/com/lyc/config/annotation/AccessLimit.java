package com.lyc.config.annotation;

import java.lang.annotation.*;

/**
 * 操作限流注解
 *
 * @author 刘怡畅
 */
@Documented     //注解是否包含在javaDoc
@Target(ElementType.METHOD)     //作用目标
@Retention(RetentionPolicy.RUNTIME)     //生命周期
public @interface AccessLimit {

    /**
     * 限制周期(秒)
     */
    int seconds();

    /**
     * 规定周期内限制次数
     */
    int maxCount();

    /**
     * 触发限制时的消息提示
     */
    String msg() default "操作过于频繁请稍后再试";

}

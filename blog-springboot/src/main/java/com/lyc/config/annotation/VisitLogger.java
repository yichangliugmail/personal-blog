package com.lyc.config.annotation;

import java.lang.annotation.*;

/**
 * @author 刘怡畅
 * @description
 * @date 2023/5/3 20:46
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VisitLogger {

    /**
     * @return 访问页面
     */
    String value() default "";
}

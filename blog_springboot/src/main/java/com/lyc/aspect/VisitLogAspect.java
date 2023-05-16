package com.lyc.aspect;

import com.lyc.annotation.VisitLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author 刘怡畅
 * @description 基于AOP实现用户访问日志记录
 * @date 2023/5/16 16:40
 */

@Aspect
@Component
public class VisitLogAspect {

    /**
     * 设置切入点
     */
    @Pointcut("@annotation(com.lyc.annotation.VisitLogger)")
    public void visitLogPointcut(){}

    /**
     * 指定通知类型为AfterReturning
     * @param joinPoint 连接点
     * @param result 数据
     */
    @AfterReturning(value = "visitLogPointcut()",returning = "result")
    public void doAfterReturning(JoinPoint joinPoint,Object result){
        //获取目标方法中的信息
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        //获取目标方法
        Method method = signature.getMethod();
        //访问页面，获取注解中的value
        VisitLogger visitLogger = method.getAnnotation(VisitLogger.class);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

    }


}

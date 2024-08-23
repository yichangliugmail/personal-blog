package com.lyc.aspect;

import com.lyc.config.annotation.VisitLogger;
import com.lyc.manager.AsyncManager;
import com.lyc.manager.factory.AsyncFactory;
import com.lyc.model.po.VisitLog;
import com.lyc.utils.IpUtils;
import com.lyc.utils.UserAgentUtils;
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
import java.util.Map;
import java.util.Objects;
import java.util.TimerTask;

/**
 * @author liuYichang
 * @description 基于AOP实现用户访问日志记录
 * @date 2023/5/16 16:40
 */

@Aspect
@Component
public class VisitLogAspect {

    /**
     * 设置切入点
     */
    @Pointcut("@annotation(com.lyc.config.annotation.VisitLogger)")
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
        //解析browser和os
        Map<String, String> userAgentMap = UserAgentUtils.parseOsAndBrowser(request.getHeader("User-Agent"));
        String ipAddress = IpUtils.getIpAddress(request);
        String ipSource = IpUtils.getIpSource(ipAddress);

        //封装对象
        VisitLog visitLog=VisitLog.builder()
                .browser(userAgentMap.get("browser"))
                .os(userAgentMap.get("os"))
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .page(visitLogger.value())
                .build();

        TimerTask timerTask = AsyncFactory.recordVisitLog(visitLog);
        AsyncManager.getInstance().execute(timerTask);
    }


}

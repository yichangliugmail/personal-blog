package com.lyc.aspect;

import com.alibaba.fastjson2.JSON;
import com.lyc.manager.AsyncManager;
import com.lyc.manager.factory.AsyncFactory;
import com.lyc.model.po.ExceptionLog;
import com.lyc.utils.IpUtils;
import com.lyc.utils.UserAgentUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.TimerTask;

/**
 * @author 刘怡畅
 * @description 基于AOP实现异常日志捕获
 * @date 2023/4/30 16:39
 */

@Aspect
@RestControllerAdvice
public class ExceptionLogAspect {

    /**
     * 设置异常日志切入点，扫描controller下的包
     */
    @Pointcut("execution(* com.lyc.controller..*.*(..))")
    public void exceptionLogPointCut(){}

    /**
     * 异常通知，指定通知类型为AfterThrowing
     * @param joinPoint 封装了切面方法的信息
     * @param e 异常
     */
    @AfterThrowing(pointcut = "exceptionLogPointCut()",throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint,Throwable e){
        //获取目标方法中的一些信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        // 获取操作
        Api api = (Api) signature.getDeclaringType().getAnnotation(Api.class);
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        // 封装异常日志对象
        ExceptionLog exceptionLog = new ExceptionLog();
        // 异常模块
        exceptionLog.setModule(api.tags()[0]);
        // 请求URI
        exceptionLog.setUri(request.getRequestURI());
        // 异常名称
        exceptionLog.setName(e.getClass().getName());
        // 操作描述
        exceptionLog.setDescription(apiOperation.value());
        // 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取请求的方法名
        String methodName = method.getName();
        methodName = className + "." + methodName;
        // 异常方法名称
        exceptionLog.setErrorMethod(methodName);
        // 异常信息
        exceptionLog.setMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
        // 请求参数
        if (joinPoint.getArgs()[0] instanceof MultipartFile) {
            exceptionLog.setParams(((MultipartFile) joinPoint.getArgs()[0]).getOriginalFilename());
        } else {
            exceptionLog.setParams(JSON.toJSONString(joinPoint.getArgs()));
        }
        // 请求方式
        exceptionLog.setRequestMethod(Objects.requireNonNull(request).getMethod());
        // 操作ip和操作地址
        String ip = IpUtils.getIpAddress(request);
        exceptionLog.setIpAddress(ip);
        exceptionLog.setIpSource(IpUtils.getIpSource(ip));
        // 操作系统和浏览器
        Map<String, String> userAgentMap = UserAgentUtils.parseOsAndBrowser(request.getHeader("User-Agent"));
        exceptionLog.setOs(userAgentMap.get("os"));
        exceptionLog.setBrowser(userAgentMap.get("browser"));
        // 创建一个定时任务，将数据保存到数据库，定时任务使用多线程完成
        TimerTask timerTask = AsyncFactory.recordException(exceptionLog);
        AsyncManager.getInstance().execute(timerTask);
    }

    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stet : elements) {
            stringBuilder.append(stet).append("\n");
        }
        return exceptionName + ":" + exceptionMessage + "\n" + stringBuilder;
    }

}

package com.lyc.config.satoken;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.json.JSONUtil;
import com.lyc.interceptor.AccessLimitInterceptor;
import com.lyc.interceptor.PageableInterceptor;
import com.lyc.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.lyc.constant.enums.StatusCodeEnum.UNAUTHORIZED;

/**
 * SaToken配置，配置拦截器
 *
 * @author 刘怡畅
 * @date 2022/11/28 22:12
 **/
@Component
public class SaTokenConfig implements WebMvcConfigurer {

    @Autowired
    private AccessLimitInterceptor accessLimitInterceptor;

    /**
     * 放行路径
     */
    private final String[] EXCLUDE_PATH_PATTERNS = {
            "/swagger-resources",
            "/webjars/**",
            "/v2/api-docs",
            "/doc.html",
            "/favicon.ico",
            "/login",
            "/oauth/*",
    };

    /**
     * 注册各种拦截器
     * @param registry 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册分页拦截器
        registry.addInterceptor(new PageableInterceptor());
        // 注册Redis限流器
        registry.addInterceptor(accessLimitInterceptor);
        // 注册 Sa-Token 的注解拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                // 先拦截所有拦截路径
                .addInclude("/**")
                // 再配置放行路径
                .addExclude(EXCLUDE_PATH_PATTERNS)
                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(obj -> {
                    SaHolder.getResponse()
                            // 允许指定域访问跨域资源
                            .setHeader("Access-Control-Allow-Origin", "*")
                            // 允许所有请求方式
                            .setHeader("Access-Control-Allow-Methods", "*")
                            // 有效时间
                            .setHeader("Access-Control-Max-Age", "3600")
                            // 允许的header参数
                            .setHeader("Access-Control-Allow-Headers", "*");
                    // 如果是预检请求，则立即返回到前端
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .free(r -> System.out.println("--------OPTIONS预检请求，不做处理"))
                            .back();
                })
                // 认证函数: 每次请求执行
                .setAuth(obj -> {
                    // 检查是否登录
                    SaRouter.match("/admin/**").check(r -> StpUtil.checkLogin());
                    // 刷新token有效期
                    if (StpUtil.getTokenTimeout() < 6000) {
                        StpUtil.renewTimeout(18000);
                    }

                })
                // 异常处理函数：每次认证函数发生异常时执行此函数
                .setError(e -> {
                    // 设置响应头
                    SaHolder.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
                    if (e instanceof NotLoginException) {
                        return JSONUtil.toJsonStr(Result.fail(null,UNAUTHORIZED.getCode(), UNAUTHORIZED.getMsg()));
                    }
                    return SaResult.error(e.getMessage());
                });
    }

}
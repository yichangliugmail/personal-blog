package com.lyc.config;

import cn.hutool.core.bean.BeanException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author 刘怡畅
 * @description Spring配置类
 * @date 2023/5/2 9:54
 */
@Component
public class SpringConfig implements BeanFactoryPostProcessor {

    private static ConfigurableListableBeanFactory beanFactory;

    /**
     * 在Bean返回之前，可以对Bean的属性进行修改
     * @param beanFactory 封装了bean的一些属性
     */
    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringConfig.beanFactory=beanFactory;
    }

    /**
     * 获取对象
     * SuppressWarnings("unchecked")，抑制编译器产生的“未经检查的转换”的警告信息
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeanException{
        return (T) beanFactory.getBean(name);
    }

    /**
     * 获取对象类型
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(name);
    }

    /**
     * 通过类的class获取bean
     */
    public static <T> T getBean(Class<T> cla){
        return beanFactory.getBean(cla);
    }


}

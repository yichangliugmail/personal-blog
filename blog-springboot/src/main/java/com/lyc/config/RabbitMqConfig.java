package com.lyc.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.lyc.constant.MqConstant.*;

/**
 * @author liuYichang
 * @description rebbitMQ配置类
 * @date 2023/5/5 17:17
 */

@Configuration
public class RabbitMqConfig {

    /**
     * 消息转换器，用来将消息转换为不同格式数据
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 邮件交换机
     */
    @Bean
    public TopicExchange emailExchange(){
        return new TopicExchange(EMAIL_EXCHANGE,true,false);
    }

    /**
     * 邮件simple队列
     */
    @Bean
    public Queue emailSimpleQueue(){
        return new Queue(EMAIL_SIMPLE_QUEUE,true);
    }

    /**
     * 队列绑定到交换机
     */
    @Bean
    public Binding emailBinding(){
        return BindingBuilder.bind(emailSimpleQueue()).to(emailExchange()).with(EMAIL_SIMPLE_KEY);
    }

    /**
     * 邮件Html队列
     */
    @Bean
    public Queue emailHtmlQueue() {
        return new Queue(EMAIL_HTML_QUEUE, true);
    }

    /**
     * 绑定邮件Html队列
     */
    @Bean
    public Binding htmlQueueBinding() {
        return BindingBuilder.bind(emailHtmlQueue()).to(emailExchange()).with(EMAIL_HTML_KEY);
    }


}

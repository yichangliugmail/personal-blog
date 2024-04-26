package com.lyc.constant;

/**
 * 消息中间件常量
 *
 * @author 刘怡畅
 */
public class MqConstant {
    /**
     * 邮件交换机
     */
    public static final String EMAIL_EXCHANGE="email.topic";

    /**
     * 邮件simple队列
     */
    public static final String EMAIL_SIMPLE_QUEUE="email.simple.queue";

    /**
     * 邮件Simple RoutingKey
     */
    public static final String EMAIL_SIMPLE_KEY = "email.simple.key";

    /**
     * 邮件HTML队列
     */
    public static final String EMAIL_HTML_QUEUE = "email.html.queue";

    /**
     * 邮件Html RoutingKey
     */
    public static final String EMAIL_HTML_KEY = "email.html.key";


}

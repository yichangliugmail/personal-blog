package com.lyc.service;

import com.lyc.model.dto.MailDTO;

/**
 * @author liuYichang
 * @description 邮件服务接口
 * @date 2023/5/5 19:15
 */
public interface EmailService {

    /**
     * 发送简单邮件
     * @param mailDTO 邮件信息
     */
    void sendSimpleMail(MailDTO mailDTO);

    /**
     * 发送HTML邮件
     * @param mailDTO 邮件信息
     */
    void sendHtmlMail(MailDTO mailDTO);

}

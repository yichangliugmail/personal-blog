package com.lyc.service.impl;

import com.lyc.model.dto.MailDTO;
import com.lyc.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author liuYichang
 * @description
 * @date 2023/5/5 19:18
 */

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendSimpleMail(MailDTO mailDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(mailDTO.getToEmail());
        message.setSubject(mailDTO.getSubject());
        message.setText(mailDTO.getContent());
        javaMailSender.send(message);
    }

    @Override
    public void sendHtmlMail(MailDTO mailDTO) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            Context context = new Context();
            context.setVariables(mailDTO.getContentMap());
            String process = templateEngine.process(mailDTO.getTemplate(), context);
            mimeMessageHelper.setFrom(username);
            mimeMessageHelper.setTo(mailDTO.getToEmail());
            mimeMessageHelper.setSubject(mailDTO.getSubject());
            mimeMessageHelper.setText(process, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

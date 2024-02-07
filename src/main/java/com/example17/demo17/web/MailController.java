package com.example17.demo17.web;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.example17.demo17.web
 * ClassName: MailController
 * Description:
 * Create by: wangjun
 * Date: 2024/1/12 16:12
 */
@Slf4j
@RestController
public class MailController {


    @Autowired
    private JavaMailSender mailSender;


    // 发件箱（这是一个假的发件箱）
    private static String MAIL_FROM = "1270317991@qq.com";

    // 收件箱（这是一个假的收件箱）
    private static String MAIL_TO = "wangjun@sinoprof.com";

    /**
     * 发送简单邮件
     */
    @RequestMapping("sendSimpleMail")
    public String sendSimpleMail(String imgFile) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(MAIL_FROM);
            helper.setTo(MAIL_TO);
            helper.setSubject("主题：发送静态资源邮件");
            helper.setText("<html><body>< img src=\"cid:hello\"></body></html>", true);

            // 注意：addInline() 中的资源名称 hello 必须与 text 正文中 cid:hello 对应起来
            mailSender.send(mimeMessage);
            return "success";
        } catch (Exception e) {
            log.error("发送简单邮件异常", e);
            return "failure";
        }
    }

}

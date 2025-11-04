package com.example17.demo17.web;

import cn.hutool.core.date.DateUtil;
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
        int i = DateUtil.month(DateUtil.date());
        log.info("aaa"+i);
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(MAIL_FROM);
            helper.setTo(MAIL_TO);
            helper.setSubject("主题：发送静态资源邮件");
            helper.setText("尊敬的领导：<br/>截至 2025年06月30日 ，供应链管理中心共有3个产品存在断档风险，具体情况如下：<br/><html><body style='font-family: SimSun; margin: 20px;'><table border='1' cellpadding='5' cellspacing='0' style='border-collapse: collapse; width: 95%; margin: 0 auto;'><thead style='background: #f0f0f0;'><tr><th style='width: 120px;'>采购部门</th><th style='width: 120px;'>产品预警状态</th><th style='width: 120px;'>断档预警产品数量</th><th style='width: 200px;'>已启动新一期项目的细化产品数量</th></tr></thead></tbody><tr><td style='text-align: center;' rowspan='3'>集团公司/供应链管理中心/数智化管理部</td><td style='text-align: center;'>已断档</td><td style='text-align: center;'>3</td><td style='text-align: center;'>1</td></tr><tr><td style='text-align: center;'>红色预警</td><td style='text-align: center;'>0</td><td style='text-align: center;'>0</td></tr><tr><td style='text-align: center;'>黄色预警</td><td style='text-align: center;'>0</td><td style='text-align: center;'>0</td></tr><tr><td style='text-align: center;' colspan='2'>合计</td><td style='text-align: center;'>3</td><td style='text-align: center;'>1</td></tr></tbody></table></body></html><br/><br/>各部门项目具体情况请见附件。<br/>注：<br/>产品预警状态说明：<br/><br/>注：<br/>产品预警状态说明：<br/>已断档：框架协议已到期，或执行进度超100%。<br/>红色预警：框架协议不足1个月，或执行进度超90%。<br/>黄色预警：框架协议不足3个月，或执行进度超70%。", true);
            // 注意：addInline() 中的资源名称 hello 必须与 text 正文中 cid:hello 对应起来
            mailSender.send(mimeMessage);
            return "success";
        } catch (Exception e) {
            log.error("发送简单邮件异常", e);
            return "failure";
        }
    }

}

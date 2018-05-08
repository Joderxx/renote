package top.renote.util.mailUtil;

import org.springframework.beans.factory.annotation.Value;
import top.renote.util.model.Mail;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joder_X on 2017/10/2.
 */


@Component
public class MailSend {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SimpleMailMessage simpleMailMessage;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${web.host}")
    private String host;

    /**
     * 发送注册激活邮件
     * @param mail 邮件
     */
    public void sendRegisterMail(Mail mail){
        send(mail,"register.ftl");
    }

    /**
     * 发送邮件
     * @param mail 邮件
     * @param ftlName 邮件对应的freemarker模板名字
     */
    public void send(Mail mail,String ftlName) {
        dealMail(mail);
        String to = mail.getTo();
        String text = "";
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("host",host);
        if(mail.getNickname()!=null)
            map.put("nickName", mail.getNickname());
        if(mail.getContent()!=null)
            map.put("content", mail.getContent());
        if(mail.getUrl()!=null)
            map.put("url", mail.getUrl());
        try {
            // 根据模板内容，动态把map中的数据填充进去，生成HTML
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(ftlName);
            // map中的key，对应模板中的${key}表达式
            text = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendMail(to, mail.getSubject(), text);
    }

    /**
     * 发送邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    private void sendMail(String to, String subject, String content) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setFrom(simpleMailMessage.getFrom());
            if (subject != null) {
                messageHelper.setSubject(subject);
            } else {
                messageHelper.setSubject(simpleMailMessage.getSubject());
            }
            messageHelper.setTo(to);
            messageHelper.setText(content, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理邮件，修改simpleMailMessage属性
     * @param mail
     */
    private void dealMail(Mail mail){
        simpleMailMessage.setSubject("激活邮件");
        simpleMailMessage.setFrom("renote@renote.top");
        if (mail.getFrom()!=null)
            simpleMailMessage.setFrom(mail.getFrom());
        if (mail.getSubject()!=null)
            simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setSentDate(new Date());
    }
}

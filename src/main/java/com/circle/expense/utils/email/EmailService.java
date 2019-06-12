package com.circle.expense.utils.email;

import com.circle.core.util.CalendarUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Component
@Slf4j
public class EmailService {


    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    public void sendEmail(String receiver, String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText("尊敬的 "+receiver + "\n\n" + content +"\n\n日期："+ CalendarUtils.getHHHHMMDD()+"\n\n" +"轻松控团队祝您生活愉快，工作顺利！");
        try{
            mailSender.send(message);
            log.info("---SEND EMAIL TO "+to + " SUCCESS");
        }catch (Exception e){
            mailSender.send(message);
            log.error("---SEND EMAIL ERROR ---" + e.getMessage());
        }
    }

}
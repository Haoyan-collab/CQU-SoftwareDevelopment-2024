package cn.edu.cqu.nsers.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SendEmailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendVerificationCode(String sendTo, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("3261474309@qq.com");
        message.setTo(sendTo);
        message.setSubject("你的验证码");
        message.setText("你的验证码是：" + code);
        mailSender.send(message);
    }

    public String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(999999);
        return String.format("%06d", code);
    }
}

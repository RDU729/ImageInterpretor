package com.api.imageinterpretor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;


    public void sendSimpleMail(String to, String subject, String text) {
        log.info("Entering sendSimpleMail method");
        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(to);
            mailMessage.setText(subject);
            mailMessage.setSubject(text);

            javaMailSender.send(mailMessage);
            log.info("Mail sent to {}, with subject {} and body {}", to, subject, text);
        } catch (Exception e) {
            log.info("Error while Sending Mail with message {}", e.getMessage());
        }
    }

    public void sendActivationEmail(String recipient, String activationCode) {
        String activationLink = "http://localhost:8080/api/v1/activate/" /*+ recipient + "/"*/ + activationCode;
        String body = "Hi and thank you for registering. The activation link is  " + activationLink;
        sendSimpleMail(recipient, body, "Acount Activation Email");
    }
}

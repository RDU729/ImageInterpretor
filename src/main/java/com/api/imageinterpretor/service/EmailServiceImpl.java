package com.api.imageinterpretor.service;

import com.api.imageinterpretor.controller.exception.ServiceException;
import com.api.imageinterpretor.service.interfaces.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.api.imageinterpretor.exception.ErrorCodes.UNABLE_TO_SEND_EMAIL;
import static com.api.imageinterpretor.utils.Constants.*;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;


    private void sendSimpleMail(String to, String subject, String text) {
        log.info("Entering sendSimpleMail method");
        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(to);
            mailMessage.setText(subject);
            mailMessage.setSubject(text);

            javaMailSender.send(mailMessage);
            log.info("Mail sent successfully");
            log.info("Mail sent to {}, with subject {} and body {}", to, subject, text);
        } catch (Exception e) {
            log.info("Error while Sending Mail with message {}", e.getMessage());
            throw new ServiceException(UNABLE_TO_SEND_EMAIL);
        }
    }

    @Override
    public void sendActivationEmail(String recipient, String activationCode) {
        String activationLink = BASE_ACTIVATION_LINK + activationCode;
        String body = ACTIVATION_MAIL_MESSAGE + activationLink;
        sendSimpleMail(recipient, body, ACTIVATION_MAIL_SUBJECT);
    }
}

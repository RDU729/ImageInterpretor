package com.api.imageinterpretor.service;

import ch.qos.logback.core.Appender;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    EmailServiceImpl emailService;

    String email = "grifeplay@gmail.com";
    String activationCode = "activationCode";

    @Mock
    private Appender appenderMock;

    @Captor
    private ArgumentCaptor captorLoggingEvent;


    @Test
    @DisplayName("should send mail with the activation code")
    void shoould_send_email() {

        emailService.sendActivationEmail(email, activationCode);
///TODO:
    }

    @Test
    @DisplayName("should send mail with the activation code")
    void shoould_not_send_email() {

        emailService.sendActivationEmail("not_valid", activationCode);
///TODO:
    }
}
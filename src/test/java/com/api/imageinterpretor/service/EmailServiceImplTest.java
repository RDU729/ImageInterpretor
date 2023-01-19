package com.api.imageinterpretor.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    private final Logger logger = (Logger) LoggerFactory.getLogger(EmailServiceImpl.class);

    @Captor
    public ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks

    EmailServiceImpl emailService;
    String email = "grifeplay@gmail.com";
    String activationCode = "activationCode";

    @Mock
    private Appender<ILoggingEvent> mockAppender;

    @BeforeEach
    void setUp() {
        logger.addAppender(mockAppender);
        logger.setLevel(Level.ALL);
    }

    @Test
    @DisplayName("should send mail with the activation code")
    void shoould_send_email() {

        emailService.sendActivationEmail(email, activationCode);

        verify(mockAppender, times(3)).doAppend(captorLoggingEvent.capture());
        List<LoggingEvent> loggingEvents = captorLoggingEvent.getAllValues();

        assertThat(loggingEvents.get(1).getLevel()).isEqualTo(Level.INFO);
        assertThat(loggingEvents.get(1).getFormattedMessage()).isEqualTo("Mail sent successfully");

    }

    @Test
    @DisplayName("should send mail with the activation code")
    void shoould_not_send_email() {

        emailService.sendActivationEmail("not_valid", activationCode);
///TODO:
    }
}
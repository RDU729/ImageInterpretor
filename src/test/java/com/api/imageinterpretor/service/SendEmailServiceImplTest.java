package com.api.imageinterpretor.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
class SendEmailServiceImplTest {
    @Autowired
    EmailServiceImpl emailService;

    @Test
    void shpuld_send_email() throws MessagingException {
    }
}
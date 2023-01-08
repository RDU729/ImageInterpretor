package com.api.imageinterpretor.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import javax.sql.DataSource;

@SpringBootTest
class WebSecurityTest {

    @Autowired
    WebSecurity webSecurity;

    @Autowired
    DataSource dataSource;


    @Test
    void test_auth() throws Exception {
        ObjectPostProcessor objectPostProcessor = new ObjectPostProcessor() {
            @Override
            public Object postProcess(Object o) {
                return null;
            }
        };
        AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(objectPostProcessor);
        webSecurity.configAuthentication(authenticationManagerBuilder);
    }
}
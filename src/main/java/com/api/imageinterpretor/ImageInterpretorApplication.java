package com.api.imageinterpretor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@Import({ WebSecurityConfig.class })
public class ImageInterpretorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImageInterpretorApplication.class, args);
    }

}

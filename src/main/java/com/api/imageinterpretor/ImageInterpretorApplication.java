package com.api.imageinterpretor;

import com.api.imageinterpretor.config.WebSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Import({ WebSecurity.class })
public class ImageInterpretorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImageInterpretorApplication.class, args);
    }

}

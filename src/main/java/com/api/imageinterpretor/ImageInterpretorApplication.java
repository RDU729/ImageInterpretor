package com.api.imageinterpretor;

import com.api.imageinterpretor.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ WebSecurityConfig.class })
public class ImageInterpretorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImageInterpretorApplication.class, args);
    }

}

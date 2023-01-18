package com.api.imageinterpretor.config;

import com.api.imageinterpretor.model.Authorities;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.AuthoritiesRepo;
import com.api.imageinterpretor.model.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Admin {

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthoritiesRepo authoritiesRepo;


    @Bean
    void createAdmin() {
        User user = new User();
        user.setEmail("sa2@sa");
        user.setPassword("{noop}sa");
        user.setName("sa");
        user.setEnabled(1);
        user.setActivationCode("n/a");
        user.setOffence(0);
        userRepo.save(user);

        Authorities authorities = new Authorities();
        authorities.setId(2L);
        authorities.setEmail("sa2@sa");
        authorities.setAuthority("ROLE_ADMIN");
        authoritiesRepo.save(authorities);
    }
}

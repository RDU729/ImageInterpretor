package com.api.imageinterpretor.service;

import com.api.imageinterpretor.dto.SignUpDTO;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {
    private final UserRepo userRepo;

    public void signUp(SignUpDTO signUpDTO) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(signUpDTO.getPassword());
        User user = new User();
//        user.setPassword(encodedPassword);
        user.setEmail(signUpDTO.getEmail());
        userRepo.save(user);

        log.info(String.valueOf(user));
    }
}

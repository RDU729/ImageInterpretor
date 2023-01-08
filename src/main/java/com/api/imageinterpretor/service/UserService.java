package com.api.imageinterpretor.service;

import com.api.imageinterpretor.dto.SignUpDTO;
import com.api.imageinterpretor.model.Authorities;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.AuthoritiesRepo;
import com.api.imageinterpretor.model.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepo userRepo;
    private final AuthoritiesRepo authRepo;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void signUp(SignUpDTO signUpDTO) {

        User user = new User();
        String encodedPass = passwordEncoder().encode(signUpDTO.getPassword());
        String noopEncodedPass = "{noop}" + encodedPass;

        user.setName(signUpDTO.getName());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(noopEncodedPass);
        user.setEnabled(0);
        userRepo.save(user);

        Authorities authorities = new Authorities();

        authorities.setEmail(signUpDTO.getEmail());
        authorities.setAuthority("ROLE_USER");
        authRepo.save(authorities);

        log.info(String.valueOf(user));
    }
}

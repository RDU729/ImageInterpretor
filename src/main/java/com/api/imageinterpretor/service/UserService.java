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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepo userRepo;
    private final AuthoritiesRepo authRepo;
    private final EmailServiceImpl emailService;

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
        //UUID.randomUUID()

        String activationCode = String.valueOf(UUID.randomUUID());
        user.setActivationCode(activationCode);
        userRepo.save(user);

        Authorities authorities = new Authorities();

        authorities.setEmail(signUpDTO.getEmail());
        authorities.setAuthority("ROLE_USER");
        authRepo.save(authorities);

        emailService.sendActivationEmail(signUpDTO.getEmail(), activationCode);

        log.info(String.valueOf(user));
    }

    public void activateAccount(/*String email,*/ String uuid) {
        List<User> all = userRepo.findAll();
        for (var e : all)
            if (e.getActivationCode().equals(uuid)) {
                e.setEnabled(1);
                userRepo.save(e);
            }
    }

//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//
//            if (user.getActivationCode().equals(uuid)) {
//                user.setEnabled(1);
//                userRepo.save(user);
//                //log.info("Activation successful account for {}", email);
//            } else {
//                log.info("Activation code invalid");
//            }
//        } else {
//            log.info("No user found");
//        }
}



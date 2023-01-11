package com.api.imageinterpretor.service;

import com.api.imageinterpretor.controller.exception.ServiceException;
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
import java.util.UUID;

import static com.api.imageinterpretor.exception.ErrorCodes.*;

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
        if(userRepo.findByEmail(signUpDTO.getEmail()).isEmpty()){

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
        }else {
            throw new ServiceException(USER_ALREADY_EXISTS_WITH_THIS_EMAIL);
        }
    }

    public void activateAccount(String uuid) {
        int activationCheck = 0;
        List<User> all = userRepo.findAll();
        for (var e : all)
            if (e.getActivationCode().equals(uuid)) {
                e.setEnabled(1);
                userRepo.save(e);
                log.info("Activated user with email :{} ", e.getEmail());
                activationCheck++;
            }
        if (activationCheck == 0) {
            throw new ServiceException(ACTIVATION_TOKEN_DID_NOT_MATCH_ANY_USER);
        }
        if (activationCheck > 1) {
            throw new ServiceException(ACTIVATION_TOKEN_MATCHED_MORE_THAN_ONE_USER);
        }
    }
}



package com.api.imageinterpretor.service;

import com.api.imageinterpretor.controller.exception.ServiceException;
import com.api.imageinterpretor.dto.SignUpDTO;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    private static User buildUser() {
        User user = new User();
        user.setName("Radu");
        user.setEmail("radu@gmail.com");
        user.setPassword("password");
        user.setActivationCode("activationCode");
        user.setFlow(null);
        user.setEnabled(0);
        return user;
    }


    @Test
    @Transactional
    @Rollback
    @DisplayName("Should activate account")
    void should_activate_account_successfully() {
        //Given
        User user = buildUser();

        User userfromDb = userRepo.save(user);

        //When
        userService.activateAccount(user.getActivationCode());

        //Then
        assertThat(userfromDb.getEnabled()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Should throw error when activation code invalid")
    void should_throw_error_when_activating_with_wrong_code() {
        //Given
        User user = buildUser();

        userRepo.save(user);

        //When
        ServiceException wrongCode = assertThrows(ServiceException.class, () -> {
            userService.activateAccount("wrong code");
        });

        //Then
        assertThat(wrongCode.getMessage())
                .isEqualTo("Activation token did not correspond to any user");
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Should throw error when more than one user has the save activation code")
    void should_throw_error_when_having_two_users_with_same_activation_code() {
        //Given
        User user = buildUser();
        User user2 = buildUser();
        user2.setEmail("rr@gmail.com");

        userRepo.save(user);
        userRepo.save(user2);

        //When
        ServiceException wrongCode = assertThrows(ServiceException.class, () -> {
            userService.activateAccount(user.getActivationCode());
        });

        //Then
        assertThat(wrongCode.getMessage())
                .isEqualTo("Activation token activated more than one user");
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Should signUp successfully")
    void should_sign_up_successfuly() {
        //Given
        User user = buildUser();
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setEmail(user.getEmail());
        signUpDTO.setPassword("pass");
        signUpDTO.setName("name");

        //When
        userService.signUp(signUpDTO);

        //Then
        Optional<User> userFromDb = userRepo.findByEmail(signUpDTO.getEmail());
        assertThat(userFromDb.isPresent()).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Should signUp successfully")
    void should_throw_error_given_already_registerd_user() {
        //Given
        User user = buildUser();
        userRepo.save(user);

        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setEmail(user.getEmail());
        signUpDTO.setPassword("pass");
        signUpDTO.setName("name");

        //When
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            userService.signUp(signUpDTO);
        });

        //Then
        assertThat(exception.getMessage())
                .isEqualTo("There is another account with the same email address");
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Should signUp successfully")
    void should_throw_error_given_invalid_email_format() {
        //Given
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setEmail("radu@gmail");
        signUpDTO.setPassword("pass");
        signUpDTO.setName("name");

        //When
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            userService.signUp(signUpDTO);
        });

        //Then
        assertThat(exception.getMessage())
                .isEqualTo("Email address does not appear to be valid");
    }
}
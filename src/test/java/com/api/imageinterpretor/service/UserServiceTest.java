package com.api.imageinterpretor.service;

import com.api.imageinterpretor.controller.exception.ServiceException;
import com.api.imageinterpretor.dto.LoginDTO;
import com.api.imageinterpretor.dto.SignUpDTO;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.api.imageinterpretor.service.utils.ServiceUtils.getUser;
import static com.api.imageinterpretor.utils.TestUtils.buildUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

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

    @Test
    @DisplayName("Given correct credential should login successfully")
    void test_login_successful() {
        //Given
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("sa2@sa");
        loginDTO.setPassword("sa");

        //When
        userService.login(loginDTO);

        //Then
        User currentUser = getCurrentUser();
        assertThat(currentUser.getEmail()).isEqualTo(loginDTO.getEmail());
    }

    @Test
    @DisplayName("Given incorrect credentials should not be able to login")
    void test_login_failed() {
        //Given
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("INCORRECT");
        loginDTO.setPassword("INCORRECT");

        //When
        boolean login = userService.login(loginDTO);

        //Then
        assertThat(login).isFalse();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private User getCurrentUser() {
        return getUser(userRepo);
    }

}
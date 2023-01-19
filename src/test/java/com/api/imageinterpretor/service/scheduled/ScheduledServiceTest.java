package com.api.imageinterpretor.service.scheduled;

import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Slf4j
class ScheduledServiceTest {

    @Autowired
    UserRepo userRepo1;

    ScheduledService scheduledService;

    @BeforeEach
    void setUp() {
        scheduledService = new ScheduledService(userRepo1);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Should run every minute(might change) to reactivate disabled accounts")
    void test_reactivate_user() {
        //Given
        List<User> disabledAMinuteAgo = new ArrayList<>();
        User user = new User();
        user.setEmail("usr@usr.com");
        user.setPassword("encodedPass");
        user.setName("sa");
        user.setEnabled(0);
        user.setActivationCode("n/a");
        user.setOffence(0);
        user.setDisableDate(Timestamp.valueOf(LocalDateTime.now()));
        disabledAMinuteAgo.add(user);
        userRepo1.save(user);


        //When
        scheduledService.reactivateUsers();

        //Then
        Optional<User> optionalUser = userRepo1.findByEmail(user.getEmail());
        assertThat(optionalUser).isPresent();
    }
}
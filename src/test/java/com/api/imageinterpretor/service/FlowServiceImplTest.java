package com.api.imageinterpretor.service;

import com.api.imageinterpretor.controller.exception.ServiceException;
import com.api.imageinterpretor.model.Image;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.FlowRepo;
import com.api.imageinterpretor.model.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.api.imageinterpretor.utils.TestUtils.buildUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlowServiceImplTest {

    @Mock
    FlowRepo flowRepo;

    @Mock
    UserRepo userRepo;

    FlowServiceImpl flowService;

    @BeforeEach
    void setup() {
        flowService = new FlowServiceImpl(flowRepo, userRepo);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Given user and image should create flow successfully")
    void should_create_flow() {
        //Given
        Image image = new Image();
        User user = buildUser();

        //when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(user.getEmail());
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(user));

        //When
        flowService.initFlow(image);

        //Then
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Given no active user should throw error")
    void should_throw_error_when_no_user_for_flow() {
        //Given
        Image image = new Image();
        User user = buildUser();


        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(user.getEmail());

        when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());

        //When
        ServiceException emptyOptional = assertThrows(ServiceException.class, () -> {
            flowService.initFlow(image);
        });

        //Then
        assertThat(emptyOptional.getMessage())
                .isEqualTo("Optional, contained no value");
    }
}
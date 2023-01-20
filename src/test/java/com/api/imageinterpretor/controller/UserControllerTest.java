package com.api.imageinterpretor.controller;

import com.api.imageinterpretor.dto.LoginDTO;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.UserRepo;
import com.api.imageinterpretor.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUP() {
        SecurityContextHolder.getContext().setAuthentication(null);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    @DisplayName("Provided correct credentials should authenticate successfully")
    void should_login_successfully() throws Exception {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("sa2@sa");
        loginDTO.setPassword("sa");

        this.mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))

                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Provided correct credentials should authenticate successfully")
    void should_login_fail() throws Exception {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("INCORRECT");
        loginDTO.setPassword("INCORRECT");

        this.mockMvc.perform(post("/api/v1/login")
                        .content(objectMapper.writeValueAsString(loginDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Provided correct credentials should authenticate successfully")
    void should_activate_account() throws Exception {

        User user = new User();
        user.setName("sa");
        user.setEmail("sa@sa");
        user.setPassword("sa");
        user.setEnabled(0);
        user.setOffence(0);
        user.setActivationCode("AC");

        userRepo.save(user);

        this.mockMvc.perform(get("/api/v1/activate/AC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    //@Test
    @Transactional
    @Rollback
    @DisplayName("Provided correct credentials should authenticate successfully")
    void should_activate_account_fail() throws Exception {
        this.mockMvc.perform(get("/api/v1/activate/AC")
                        .contentType(MediaType.APPLICATION_JSON));
    }

}
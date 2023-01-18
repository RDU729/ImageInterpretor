package com.api.imageinterpretor.controller;

import com.api.imageinterpretor.dto.SignUpDTO;
import com.api.imageinterpretor.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.api.imageinterpretor.utils.Constants.*;


@RestController
@RequestMapping("/api/v1")
@Slf4j
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping(value = "/signup")
    public ResponseEntity<String> processRegister(@RequestBody SignUpDTO signUpDTO) {
        userService.signUp(signUpDTO);
        return ResponseEntity.ok(SIGN_UP_RESPONSE);
    }

    @GetMapping(path = "activate/{hash}")
    public ResponseEntity<String> activateAccount(@PathVariable("hash") String uuid) {
        userService.activateAccount(uuid);
        return ResponseEntity.ok(ACCOUNT_ACTIVATED_RESPONSE);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(/*@RequestBody LoginDTO loginDTO*/) throws Exception {

        return ResponseEntity.ok(LOG_IN_RESPONSE);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok(LOGGED_OUT_RESPONSE);
    }
}

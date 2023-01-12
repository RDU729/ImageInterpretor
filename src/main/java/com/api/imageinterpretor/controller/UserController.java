package com.api.imageinterpretor.controller;

import com.api.imageinterpretor.dto.SignUpDTO;
import com.api.imageinterpretor.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@Slf4j
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping(value = "/signup")
    public ResponseEntity<String> processRegister(@RequestBody SignUpDTO signUpDTO) {
        userService.signUp(signUpDTO);
        return ResponseEntity.ok("Signup successfull. An email has been sent to your email address");
    }

    @PostMapping(path = "activate/{hash}")
    public ResponseEntity<String> activateAccount(/*@PathVariable("email") String email,*/
                                                  @PathVariable("hash") String uuid) {
        userService.activateAccount(/*email,*/uuid);
        return ResponseEntity.ok("Account activated");
    }
}

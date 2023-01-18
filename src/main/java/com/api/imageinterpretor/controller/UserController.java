package com.api.imageinterpretor.controller;

import com.api.imageinterpretor.dto.LoginDTO;
import com.api.imageinterpretor.dto.SignUpDTO;
import com.api.imageinterpretor.service.UserService;
import com.api.imageinterpretor.service.utils.ServiceUtils;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.api.imageinterpretor.utils.Constants.*;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;


@RestController
@RequestMapping("/api/v1")
@Slf4j
@AllArgsConstructor
@ApiOperation("Products API")
public class UserController {
    private UserService userService;




    @PostMapping(value = "/signup")
    public ResponseEntity<String> processRegister(@RequestBody SignUpDTO signUpDTO) {
        userService.signUp(signUpDTO);
        return ResponseEntity.ok(SIGN_UP_RESPONSE);
    }

    //@ApiOperation(value = "Activate Account", notes = " Activates the account if activation link is clicked")
    @ApiOperation(value = "Get a product by id", notes = "Returns a product as per the id")
    @GetMapping(path = "activate/{hash}")
    public ResponseEntity<String> activateAccount(@PathVariable("hash") String uuid) {
        userService.activateAccount(uuid);
        return ResponseEntity.ok(ACCOUNT_ACTIVATED_RESPONSE);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) throws Exception {
        userService.login(loginDTO);
        return ResponseEntity.ok(LOG_IN_RESPONSE);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok(LOGGED_OUT_RESPONSE);
    }
}

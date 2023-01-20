package com.api.imageinterpretor.controller;

import com.api.imageinterpretor.dto.LoginDTO;
import com.api.imageinterpretor.dto.SignUpDTO;
import com.api.imageinterpretor.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.api.imageinterpretor.utils.Constants.*;


@RestController
@RequestMapping("/api/v1")
@Slf4j
@AllArgsConstructor
@Api(value = "User Controller", description = "use to access endpoints related to user management")
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
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) throws Exception {
        boolean foundUser = userService.login(loginDTO);
        if (foundUser){
            return ResponseEntity.ok(LOG_IN_RESPONSE);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok(LOGGED_OUT_RESPONSE);
    }
}

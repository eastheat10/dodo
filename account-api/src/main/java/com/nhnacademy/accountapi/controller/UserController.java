package com.nhnacademy.accountapi.controller;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import com.nhnacademy.accountapi.dto.request.EmailRequest;
import com.nhnacademy.accountapi.dto.request.UserLoginRequest;
import com.nhnacademy.accountapi.dto.request.UserSignupRequest;
import com.nhnacademy.accountapi.dto.response.UserResponse;
import com.nhnacademy.accountapi.service.UserService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findUsers() {

        List<UserResponse> users = userService.findAll();

        return ResponseEntity.status(OK)
                             .contentType(APPLICATION_JSON)
                             .body(users);
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid UserSignupRequest signupRequest) {

        userService.doSignup(signupRequest);

        return ResponseEntity.status(CREATED)
                             .build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid UserLoginRequest loginRequest) {

        UserResponse response = userService.doLogin(loginRequest);

        return ResponseEntity.status(OK)
                             .contentType(APPLICATION_JSON)
                             .body(response);
    }

    @PostMapping("/email")
    public ResponseEntity<UserResponse> findByEmail(@RequestBody @Valid EmailRequest emailRequest) {

        UserResponse userByEmail = userService.findUserByEmail(emailRequest.getEmail());

        return ResponseEntity.status(OK)
                             .contentType(APPLICATION_JSON)
                             .body(userByEmail);
    }
}

package com.nhnacademy.gateway.controller;

import com.nhnacademy.gateway.dto.request.user.UserSignupRequest;
import com.nhnacademy.gateway.service.user.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public ModelAndView signup(UserSignupRequest userSignupRequest) {
        return new ModelAndView("user/signup-form");
    }

    @PostMapping("/signup")
    public ModelAndView doSignup(@ModelAttribute @Valid UserSignupRequest userSignupRequest,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("user/signup-form");
        }

        userService.requestSignup(userSignupRequest);

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/users/login")
    public ModelAndView login() {
        return new ModelAndView("user/login-form");
    }

    @GetMapping("/users/logout")
    public ModelAndView logout() {
        return new ModelAndView("user/logout");
    }
}

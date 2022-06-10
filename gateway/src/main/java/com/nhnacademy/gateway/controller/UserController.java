package com.nhnacademy.gateway.controller;

import com.nhnacademy.gateway.dto.request.user.UserSignupRequest;
import com.nhnacademy.gateway.service.UserSignUpService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserSignUpService userSignUpService;

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

        userSignUpService.requestSignup(userSignupRequest);

        return new ModelAndView("redirect:/");
    }
}

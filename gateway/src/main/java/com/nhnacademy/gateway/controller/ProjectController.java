package com.nhnacademy.gateway.controller;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    @GetMapping
    public ModelAndView projects(Principal principal) {
        return new ModelAndView();
    }
}

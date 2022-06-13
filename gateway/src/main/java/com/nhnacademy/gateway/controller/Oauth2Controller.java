package com.nhnacademy.gateway.controller;

import com.nhnacademy.gateway.dto.response.oauth.GithubProfile;
import com.nhnacademy.gateway.exception.WrongEmailException;
import com.nhnacademy.gateway.service.user.OAuth2Service;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final OAuth2Service oAuth2Service;

    @GetMapping("/github")
    public RedirectView githubLogin() {
        return new RedirectView(oAuth2Service.getRedirectUrl());
    }

    @GetMapping("/oauth2/code/github")
    public ModelAndView githubRedirect(@RequestParam("code") String code,
                                       HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException, WrongEmailException {

        GithubProfile profile = oAuth2Service.getProfile(code);

        boolean existUser =
            oAuth2Service.verifyResidentByEmail(profile.getEmail(), request, response);

        if (!existUser) {
            throw new WrongEmailException();
        }

        return new ModelAndView("redirect:/");
    }
}

package com.nhnacademy.gateway.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.gateway.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

//@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserController.class)
@WithMockUser
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final String username = "username";
    private final String password = "password";
    private final String email = "email@gmail.com";

    @Test
    @DisplayName("회원가입")
    void testDoSignup() throws Exception {

        mockMvc.perform(post("/signup")
                   .param("username", username)
                   .param("password", password)
                   .param("email", email)
                   .with(csrf()))
               .andExpect(status().is3xxRedirection());

        doNothing().when(userService).requestSignup(any());

        verify(userService, times(1)).requestSignup(any());
    }

    @Test
    @DisplayName("회원가입 양식 오류")
    void testSignupFormError() throws Exception {

        mockMvc.perform(post("/signup")
                   .param("username", "")
                   .param("password", "")
                   .param("email", "")
                   .with(csrf()))
               .andExpect(status().isOk())
               .andExpect(view().name("user/signup-form"));
    }
}
package com.nhnacademy.accountapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.accountapi.dto.request.EmailRequest;
import com.nhnacademy.accountapi.dto.request.UserLoginRequest;
import com.nhnacademy.accountapi.dto.request.UserSignupRequest;
import com.nhnacademy.accountapi.dto.response.UserResponse;
import com.nhnacademy.accountapi.entity.User;
import com.nhnacademy.accountapi.exception.LoginUserNotFoundException;
import com.nhnacademy.accountapi.exception.UsernameOverlapException;
import com.nhnacademy.accountapi.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    private final String username = "username";
    private final String password = "password";
    private final String email = "email@e.mail";

    @Test
    @DisplayName("회원가입")
    void testSignup() throws Exception {

        UserSignupRequest signup = new UserSignupRequest();

        ReflectionTestUtils.setField(signup, "username", username);
        ReflectionTestUtils.setField(signup, "password", password);
        ReflectionTestUtils.setField(signup, "email", email);

        doNothing().when(userService).doSignup(signup);

        mockMvc.perform(post("/users/signup")
                   .contentType(APPLICATION_JSON)
                   .content(mapper.writeValueAsString(signup)))
               .andExpect(status().isCreated())
               .andDo(print());
    }

    @Test
    @DisplayName("회원가입시 아이디 중복")
    void testSignup_fail() throws Exception {

        UserSignupRequest signup = new UserSignupRequest();

        ReflectionTestUtils.setField(signup, "username", username);
        ReflectionTestUtils.setField(signup, "password", password);
        ReflectionTestUtils.setField(signup, "email", email);

        doThrow(new UsernameOverlapException(signup.getUsername()))
            .when(userService).doSignup(any());

        mockMvc.perform(post("/users/signup")
                   .contentType(APPLICATION_JSON)
                   .content(mapper.writeValueAsString(signup)))
               .andExpect(status().isBadRequest())
               .andDo(print());
    }

    @Test
    @DisplayName("회원가입시 요청 데이터 검증 오류")
    void testValidation() throws Exception {

        UserSignupRequest signup = new UserSignupRequest();

        mockMvc.perform(post("/users/signup")
                   .contentType(APPLICATION_JSON)
                   .content(mapper.writeValueAsString(signup)))
               .andExpect(status().isBadRequest())
               .andDo(print());
    }

    @Test
    @DisplayName("로그인")
    void testLogin() throws Exception {

        UserLoginRequest loginRequest = new UserLoginRequest();
        ReflectionTestUtils.setField(loginRequest, "username", username);

        User user = User.builder()
                        .username(username)
                        .password(password)
                        .build();

        UserResponse userResponse = new UserResponse(user);

        given(userService.doLogin(any()))
            .willReturn(userResponse);

        mockMvc.perform(post("/users/login")
                   .contentType(APPLICATION_JSON)
                   .accept(APPLICATION_JSON)
                   .content(mapper.writeValueAsString(loginRequest)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(APPLICATION_JSON))
               .andExpect(jsonPath("$.username", equalTo(username)))
               .andExpect(jsonPath("$.password", equalTo(password)))
               .andDo(print());
    }

    @Test
    @DisplayName("로그인 시 아이디 또는 비밀번호 틀림")
    void testLoginFail() throws Exception {

        UserLoginRequest request = new UserLoginRequest();

        ReflectionTestUtils.setField(request, "username", username);

        given(userService.doLogin(any())).willThrow(LoginUserNotFoundException.class);

        mockMvc.perform(post("/users/login")
                   .contentType(APPLICATION_JSON)
                   .accept(APPLICATION_JSON)
                   .content(mapper.writeValueAsString(request)))
               .andExpect(status().isBadRequest())
               .andDo(print());
    }

    @Test
    @DisplayName("아이디 비밀번호 양식 오류")
    void testLoginFail_formatException() throws Exception {

        UserLoginRequest request = new UserLoginRequest();

        mockMvc.perform(post("/users/login")
                   .contentType(APPLICATION_JSON)
                   .accept(APPLICATION_JSON)
                   .content(mapper.writeValueAsString(request)))
               .andExpect(status().isBadRequest())
               .andDo(print());
    }

    @Test
    @DisplayName("이메일로 회원 찾기")
    void testFindUserByEmail() throws Exception {

        EmailRequest emailRequest = new EmailRequest();
        ReflectionTestUtils.setField(emailRequest, "email", "eastheat10@gmail.com");

        User user = User.builder()
                        .username(username)
                        .password(password)
                        .email("eastheat10@gmail.com")
                        .build();

        UserResponse userResponse = new UserResponse(user);

        given(userService.findUserByEmail(anyString())).willReturn(userResponse);

        mockMvc.perform(post("/users/email")
                   .contentType(APPLICATION_JSON)
                   .accept(APPLICATION_JSON)
                   .content(mapper.writeValueAsString(emailRequest)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(APPLICATION_JSON))
               .andExpect(jsonPath("$.email", equalTo(emailRequest.getEmail())));
    }
}
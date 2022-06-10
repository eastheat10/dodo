package com.nhnacademy.accountapi.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.accountapi.dto.request.UserLoginRequest;
import com.nhnacademy.accountapi.dto.request.UserSignupRequest;
import com.nhnacademy.accountapi.dto.response.UserResponse;
import com.nhnacademy.accountapi.entity.UserStatus;
import com.nhnacademy.accountapi.entity.User;
import com.nhnacademy.accountapi.exception.UsernameOverlapException;
import com.nhnacademy.accountapi.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService usersService;

    @Mock
    private UserRepository userRepository;

    String username = "username";
    String password = "password";
    String email = "email@e.mail";

    @Test
    @DisplayName("회원가입")
    void doSignup() {

        User user = getUser();

        UserSignupRequest request = new UserSignupRequest();

        ReflectionTestUtils.setField(request, "username", username);
        ReflectionTestUtils.setField(request, "password", password);
        ReflectionTestUtils.setField(request, "email", email);

        given(userRepository.save(any())).willReturn(user);

        usersService.doSignup(request);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("아이디 중복으로 회원가입 실패")
    void testSignupFail() {

        User user = getUser();

        UserSignupRequest request = new UserSignupRequest();

        ReflectionTestUtils.setField(request, "username", username);
        ReflectionTestUtils.setField(request, "password", password);
        ReflectionTestUtils.setField(request, "email", email);

        given(userRepository.save(any())).willReturn(user);

        usersService.doSignup(request);

        given(userRepository.existsByUsername(username)).willReturn(true);

        assertThatThrownBy(() -> usersService.doSignup(request))
            .isInstanceOf(UsernameOverlapException.class);
    }

    @Test
    @DisplayName("로그인 요청")
    void testDoLogin() {

        UserLoginRequest request = new UserLoginRequest();

        ReflectionTestUtils.setField(request, "username", username);

        User user = getUser();

        UserResponse response = new UserResponse(user);

        given(
            userRepository.findByUsername(request.getUsername()))
            .willReturn(Optional.of(user));

        UserResponse loginResponse = usersService.doLogin(request);

        verify(userRepository, times(1)).findByUsername(anyString());
        assertThat(loginResponse.getUsername()).isEqualTo(response.getUsername());
        assertThat(loginResponse.getPassword()).isEqualTo(response.getPassword());
    }

    @Test
    @DisplayName("이메일로 회원 찾기")
    void testFindUserByEmail() {

        User user = getUser();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        UserResponse userByEmail = usersService.findUserByEmail(email);

        verify(userRepository, times(1)).findByEmail(anyString());
        assertThat(userByEmail.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("사용자 휴면 처리")
    void testUserMakeDormant() {

        User user = spy(getUser());

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        lenient().when(user.getId()).thenReturn(1L);

        usersService.makeStatusDormant(1L);

        verify(user, times(1)).makeDormant();
        assertThat(user.getStatus()).isEqualTo(UserStatus.DORMANT);
    }

    @Test
    @DisplayName("사용자 삭제")
    void testUserDelete() {

        User user = spy(getUser());

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        lenient().when(user.getId()).thenReturn(1L);

        usersService.deleteUser(1L);

        verify(user, times(1)).deleteUser();
        assertThat(user.getStatus()).isEqualTo(UserStatus.DELETED);
    }

    private User getUser() {
        return User.builder()
                   .username(username)
                   .password(password)
                   .email(email)
                   .build();
    }

}
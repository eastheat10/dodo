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
import com.nhnacademy.accountapi.entity.Status;
import com.nhnacademy.accountapi.entity.Users;
import com.nhnacademy.accountapi.exception.UsernameOverlapException;
import com.nhnacademy.accountapi.repository.UsersRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersRepository usersRepository;

    String username = "username";
    String password = "password";
    String email = "email@e.mail";

    @Test
    @DisplayName("회원가입")
    void doSignup() {

        Users users = getUser();

        UserSignupRequest request = new UserSignupRequest();
        UserResponse response = new UserResponse(users);

        ReflectionTestUtils.setField(request, "username", username);
        ReflectionTestUtils.setField(request, "password", password);
        ReflectionTestUtils.setField(request, "email", email);

        given(usersRepository.save(any())).willReturn(users);

        UserResponse savedUser = usersService.doSignup(request);

        verify(usersRepository, times(1)).save(any());
        assertThat(savedUser.getStatus()).isEqualTo(Status.JOIN);
        assertThat(savedUser.getUsername()).isEqualTo(response.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(response.getPassword());
    }

    @Test
    @DisplayName("아이디 중복으로 회원가입 실패")
    void testSignupFail() {

        Users users = getUser();

        UserSignupRequest request = new UserSignupRequest();

        ReflectionTestUtils.setField(request, "username", username);
        ReflectionTestUtils.setField(request, "password", password);
        ReflectionTestUtils.setField(request, "email", email);

        given(usersRepository.save(any())).willReturn(users);

        usersService.doSignup(request);

        given(usersRepository.existsByUsername(username)).willReturn(true);

        assertThatThrownBy(() -> usersService.doSignup(request))
            .isInstanceOf(UsernameOverlapException.class);
    }

    @Test
    @DisplayName("로그인 요청")
    void testDoLogin() {

        UserLoginRequest request = new UserLoginRequest();

        ReflectionTestUtils.setField(request, "username", username);
        ReflectionTestUtils.setField(request, "password", password);

        Users users = getUser();

        UserResponse response = new UserResponse(users);

        given(
            usersRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword()))
            .willReturn(Optional.of(users));

        UserResponse loginResponse = usersService.doLogin(request);

        verify(usersRepository, times(1)).findByUsernameAndPassword(anyString(), anyString());
        assertThat(loginResponse.getUsername()).isEqualTo(response.getUsername());
        assertThat(loginResponse.getPassword()).isEqualTo(response.getPassword());
    }

    @Test
    @DisplayName("이메일로 회원 찾기")
    void testFindUserByEmail() {

        Users users = getUser();

        given(usersRepository.findByEmail(email)).willReturn(Optional.of(users));

        UserResponse userByEmail = usersService.findUserByEmail(email);

        verify(usersRepository, times(1)).findByEmail(anyString());
        assertThat(userByEmail.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("사용자 휴면 처리")
    void testUserMakeDormant() {

        Users users = spy(getUser());

        given(usersRepository.findById(anyLong())).willReturn(Optional.of(users));
        lenient().when(users.getId()).thenReturn(1L);

        usersService.makeStatusDormant(1L);

        verify(users, times(1)).makeDormant();
        assertThat(users.getStatus()).isEqualTo(Status.DORMANT);
    }

    @Test
    @DisplayName("사용자 삭제")
    void testUserDelete() {

        Users users = spy(getUser());

        given(usersRepository.findById(anyLong())).willReturn(Optional.of(users));
        lenient().when(users.getId()).thenReturn(1L);

        usersService.deleteUser(1L);

        verify(users, times(1)).deleteUser();
        assertThat(users.getStatus()).isEqualTo(Status.DELETED);
    }

    private Users getUser() {
        return Users.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .build();
    }

}
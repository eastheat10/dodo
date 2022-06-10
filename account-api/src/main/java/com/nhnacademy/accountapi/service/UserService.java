package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.dto.request.UserLoginRequest;
import com.nhnacademy.accountapi.dto.request.UserSignupRequest;
import com.nhnacademy.accountapi.dto.response.UserResponse;
import com.nhnacademy.accountapi.entity.User;
import com.nhnacademy.accountapi.exception.EmailNotFoundException;
import com.nhnacademy.accountapi.exception.LoginUserNotFoundException;
import com.nhnacademy.accountapi.exception.UsernameOverlapException;
import com.nhnacademy.accountapi.exception.UserNoFoundException;
import com.nhnacademy.accountapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void doSignup(UserSignupRequest user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameOverlapException(user.getUsername());
        }

        User users = User.builder()
                         .username(user.getUsername())
                         .password(user.getPassword())
                         .email(user.getEmail())
                         .build();

        log.info("회원가입 성공 = {}", users);

        userRepository.save(users);
    }

    public UserResponse doLogin(UserLoginRequest loginUser) {

        User user =
            userRepository.findByUsername(loginUser.getUsername())
                          .orElseThrow(LoginUserNotFoundException::new);

        return new UserResponse(user);
    }

    public UserResponse findUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                                  .orElseThrow(EmailNotFoundException::new);

        return new UserResponse(user);
    }

    @Transactional
    public void makeStatusDormant(Long id) {

        User user = userRepository.findById(id)
                                  .orElseThrow(UserNoFoundException::new);

        user.makeDormant();
    }

    @Transactional
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                                  .orElseThrow(UserNoFoundException::new);

        user.deleteUser();
    }
}

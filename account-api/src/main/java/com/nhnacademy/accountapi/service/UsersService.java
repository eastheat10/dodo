package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.dto.request.UserLoginRequest;
import com.nhnacademy.accountapi.dto.request.UserSignupRequest;
import com.nhnacademy.accountapi.dto.response.UserResponse;
import com.nhnacademy.accountapi.entity.Users;
import com.nhnacademy.accountapi.exception.IllegalEmailException;
import com.nhnacademy.accountapi.exception.LoginUserNotFoundException;
import com.nhnacademy.accountapi.exception.UsernameOverlapException;
import com.nhnacademy.accountapi.exception.UserNoFoundException;
import com.nhnacademy.accountapi.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    @Transactional
    public UserResponse doSignup(UserSignupRequest user) {

        if (usersRepository.existsByUsername(user.getUsername())) {
            throw new UsernameOverlapException(user.getUsername());
        }

        Users users = Users.builder()
                           .username(user.getUsername())
                           .password(user.getPassword())
                           .email(user.getEmail())
                           .build();

        Users savedUser = usersRepository.save(users);

        return new UserResponse(savedUser);
    }

    public UserResponse doLogin(UserLoginRequest loginUser) {

        Users users =
            usersRepository.findByUsernameAndPassword(loginUser.getUsername(),
                               loginUser.getPassword())
                           .orElseThrow(LoginUserNotFoundException::new);

        return new UserResponse(users);
    }

    public UserResponse findUserByEmail(String email) {

        Users users = usersRepository.findByEmail(email)
                                     .orElseThrow(IllegalEmailException::new);

        return new UserResponse(users);
    }

    @Transactional
    public void makeStatusDormant(Long id) {

        Users users = usersRepository.findById(id)
                                     .orElseThrow(UserNoFoundException::new);

        users.makeDormant();
    }

    @Transactional
    public void deleteUser(Long id) {

        Users users = usersRepository.findById(id)
                                     .orElseThrow(UserNoFoundException::new);

        users.deleteUser();
    }
}

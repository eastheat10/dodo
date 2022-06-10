package com.nhnacademy.accountapi.dto.response;

import com.nhnacademy.accountapi.entity.UserStatus;
import com.nhnacademy.accountapi.entity.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private final String username;
    private final String password;
    private final String email;
    private final UserStatus userStatus;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.userStatus = user.getStatus();
    }
}

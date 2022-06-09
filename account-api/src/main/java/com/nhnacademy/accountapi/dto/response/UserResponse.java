package com.nhnacademy.accountapi.dto.response;

import com.nhnacademy.accountapi.entity.Status;
import com.nhnacademy.accountapi.entity.Users;
import lombok.Getter;

@Getter
public class UserResponse {

    private final String username;
    private final String password;
    private final String email;
    private final Status status;

    public UserResponse(Users users) {
        this.username = users.getUsername();
        this.password = users.getPassword();
        this.email = users.getEmail();
        this.status = users.getStatus();
    }
}

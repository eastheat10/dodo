package com.nhnacademy.gateway.dto.response.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {

    private String username;
    private String password;
    private String email;
    private String userStatus;
}

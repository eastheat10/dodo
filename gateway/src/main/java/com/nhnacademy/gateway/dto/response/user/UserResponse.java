package com.nhnacademy.gateway.dto.response.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String userStatus;
}

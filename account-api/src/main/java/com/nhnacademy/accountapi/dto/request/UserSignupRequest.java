package com.nhnacademy.accountapi.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupRequest {

    private String username;
    private String password;
    private String email;
}

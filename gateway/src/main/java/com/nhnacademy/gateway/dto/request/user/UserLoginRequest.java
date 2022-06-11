package com.nhnacademy.gateway.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginRequest {

    private final String username;
}

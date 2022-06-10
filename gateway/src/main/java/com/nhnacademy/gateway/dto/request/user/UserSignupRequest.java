package com.nhnacademy.gateway.dto.request.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserSignupRequest {

    @NotBlank
    private final String username;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private final String email;

    public void passwordEncoding(String password) {
        this.password = password;
    }
}

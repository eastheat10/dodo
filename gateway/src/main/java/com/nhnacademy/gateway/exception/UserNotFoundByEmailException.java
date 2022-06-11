package com.nhnacademy.gateway.exception;

public class UserNotFoundByEmailException extends IllegalArgumentException {

    public UserNotFoundByEmailException(String email) {
        super("해당 이메일과 일치하는 사용자가 없습니다. :" + email);
    }
}

package com.nhnacademy.accountapi.exception;

public class LoginUserNotFoundException extends IllegalArgumentException {
    public LoginUserNotFoundException() {
        super("아이디와 비밀번호가 일치하지 않습니다.");
    }
}

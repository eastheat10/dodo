package com.nhnacademy.gateway.exception;

public class SignupFailException extends IllegalArgumentException {
    public SignupFailException() {
        super("회원가입 실패");
    }
}

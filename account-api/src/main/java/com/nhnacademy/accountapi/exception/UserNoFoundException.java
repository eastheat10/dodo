package com.nhnacademy.accountapi.exception;

public class UserNoFoundException extends IllegalArgumentException {
    public UserNoFoundException() {
        super("해당 아이디와 일치하는 회원이 없습니다.");
    }
}

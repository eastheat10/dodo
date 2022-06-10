package com.nhnacademy.accountapi.exception;

public class UsernameOverlapException extends IllegalArgumentException {
    public UsernameOverlapException(String username) {
        super(username + ": 아이디 중복");
    }
}

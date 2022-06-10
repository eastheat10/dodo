package com.nhnacademy.accountapi.exception;

public class EmailNotFoundException extends IllegalArgumentException {
    public EmailNotFoundException() {
        super("해당 이메일과 일치하는 회원이 없습니다.");
    }
}

package com.nhnacademy.accountapi.exception;

public class IllegalEmailException extends IllegalArgumentException {
    public IllegalEmailException() {
        super("해당 이메일과 일치하는 회원이 없습니다.");
    }
}

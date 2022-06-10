package com.nhnacademy.accountapi.entity;

import lombok.Getter;

public enum UserStatus {

    JOIN("가입"),
    DELETED("탈퇴"),
    DORMANT("휴면");

    UserStatus(String status) {
        this.status = status;
    }

    @Getter
    private final String status;
}

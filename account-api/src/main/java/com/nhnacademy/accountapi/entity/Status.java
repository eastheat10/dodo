package com.nhnacademy.accountapi.entity;

import lombok.Getter;

public enum Status {

    JOIN("가입"),
    DELETED("탈퇴"),
    DORMANT("휴면");

    Status(String status) {
        this.status = status;
    }

    @Getter
    private final String status;
}

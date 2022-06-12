package com.nhnacademy.gateway.dto.response.task;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PersonResponse {

    private Long taskId;
    private Long memberId;
    private String username;
}

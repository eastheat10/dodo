package com.nhnacademy.gateway.dto.request.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCommentRequest {

    private Long taskId;
    private String username;
    private String comment;
}

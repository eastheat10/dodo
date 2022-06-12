package com.nhnacademy.taskapi.dto.request.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentRequest {

    private Long taskId;
    private String username;
    private String comment;
}

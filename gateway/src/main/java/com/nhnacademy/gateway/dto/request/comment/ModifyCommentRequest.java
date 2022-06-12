package com.nhnacademy.gateway.dto.request.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyCommentRequest {

    private final Long id;
    private final String comment;
}

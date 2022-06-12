package com.nhnacademy.gateway.dto.request.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTagRequest {

    private final Long projectId;
    private final String name;
}

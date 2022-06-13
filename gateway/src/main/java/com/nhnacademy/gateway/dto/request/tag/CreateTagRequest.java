package com.nhnacademy.gateway.dto.request.tag;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTagRequest {

    @NotNull
    private final Long projectId;

    @NotBlank
    private final String name;
}

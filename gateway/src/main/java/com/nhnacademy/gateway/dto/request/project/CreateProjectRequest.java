package com.nhnacademy.gateway.dto.request.project;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CreateProjectRequest {

    @Setter
    private Long adminId;

    @Setter
    private String adminUsername;

    @NotBlank
    private final String projectName;
    @NotBlank
    private final String content;
}

package com.nhnacademy.gateway.dto.request.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateProjectRequest {

    private final Long adminId;
    private final String adminUsername;
    private final String projectName;
}

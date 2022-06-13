package com.nhnacademy.gateway.dto.request.task;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTaskRequest {

    @NotNull
    private Long projectId;
    private Long milestoneId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String registrantName;
    private List<Long> tags;
    private List<Long> people; // 담당자
}

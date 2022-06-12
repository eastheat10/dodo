package com.nhnacademy.taskapi.dto.request.task;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateTaskRequest {

    @NotBlank
    private Long projectId;

    private Long milestoneId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String registrantName;

    private List<Long> tags;
    private List<Long> people; // 담당자
}

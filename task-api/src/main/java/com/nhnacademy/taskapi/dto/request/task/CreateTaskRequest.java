package com.nhnacademy.taskapi.dto.request.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private List<Long> tags;
    private List<Long> persons; // 담당자
}

package com.nhnacademy.gateway.dto.request.task;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyTaskRequest {

    private Long taskId;
    private Long milestoneId;
    private String title;
    private String content;
    private List<Long> tags;
    private List<Long> people; // 담당자
}

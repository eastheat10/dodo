package com.nhnacademy.taskapi.dto.request.milestone;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MileStoneCreateRequest {

    private Long projectId;
    private String milestoneName;
    private LocalDate endDate;
}

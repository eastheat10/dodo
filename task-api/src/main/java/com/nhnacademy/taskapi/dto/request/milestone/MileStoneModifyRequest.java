package com.nhnacademy.taskapi.dto.request.milestone;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MileStoneModifyRequest {

    private Long id;
    private String milestoneName;
    private LocalDate startDate;
    private LocalDate endDate;
}

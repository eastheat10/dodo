package com.nhnacademy.gateway.dto.request.milestone;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyMileStoneRequest {

    private Long id;
    private String milestoneName;
    private LocalDate startDate;
    private LocalDate endDate;
}

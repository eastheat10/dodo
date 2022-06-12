package com.nhnacademy.gateway.dto.request.milestone;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateMileStoneRequest {

    private final Long projectId;
    private final String milestoneName;
    private final LocalDate startDate;
    private final LocalDate endDate;
}


package com.nhnacademy.taskapi.dto.projection;

import java.time.LocalDate;

public interface MilestoneDto {

    Long getId();

    Long getProjectId();

    String getName();

    LocalDate getStartDate();

    LocalDate getEndDate();
}

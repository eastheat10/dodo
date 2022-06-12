package com.nhnacademy.taskapi.dto.response.milestone;

import com.nhnacademy.taskapi.dto.projection.MilestoneDto;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MilestoneResponse {

    private final Long id;
    private final Long projectId;
    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public MilestoneResponse(MilestoneDto milestone) {
        this.id = milestone.getId();
        this.projectId = milestone.getId();
        this.name = milestone.getName();
        this.startDate = milestone.getStartDate();
        this.endDate = milestone.getEndDate();
    }
}

package com.nhnacademy.gateway.service;

import com.nhnacademy.gateway.adapter.MilestoneAdapter;
import com.nhnacademy.gateway.dto.request.milestone.CreateMilestoneRequest;
import com.nhnacademy.gateway.dto.request.milestone.ModifyMileStoneRequest;
import com.nhnacademy.gateway.dto.response.milestone.MilestoneResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MilestoneService {

    private final MilestoneAdapter milestoneAdapter;

    public void createMilestone(final CreateMilestoneRequest createRequest) {

        milestoneAdapter.createMilestone(createRequest);
    }

    public MilestoneResponse findMilestone(Long id) {

        return milestoneAdapter.findMilestone(id);
    }

    public List<MilestoneResponse> findMilestonesByProjectId(Long projectId) {

        return milestoneAdapter.findMilestonesByProjectId(projectId);
    }

    public void modifyMilestone(ModifyMileStoneRequest modifyRequest) {

        milestoneAdapter.modifyMilestone(modifyRequest);
    }

    public void deleteMilestone(Long id) {

        milestoneAdapter.deleteMilestone(id);
    }
}

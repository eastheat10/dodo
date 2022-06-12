package com.nhnacademy.taskapi.service;

import static java.util.stream.Collectors.*;

import com.nhnacademy.taskapi.dto.projection.MilestoneDto;
import com.nhnacademy.taskapi.dto.request.milestone.MileStoneCreateRequest;
import com.nhnacademy.taskapi.dto.request.milestone.MileStoneModifyRequest;
import com.nhnacademy.taskapi.dto.response.milestone.MilestoneListResponse;
import com.nhnacademy.taskapi.dto.response.milestone.MilestoneResponse;
import com.nhnacademy.taskapi.entity.Milestone;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.exception.MilestoneNotFoundException;
import com.nhnacademy.taskapi.exception.ProjectNotFoundException;
import com.nhnacademy.taskapi.repository.MilestoneRepository;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public void createMileStone(MileStoneCreateRequest mileStoneRequest) {

        Project project = projectRepository.findById(mileStoneRequest.getProjectId())
                                           .orElseThrow(ProjectNotFoundException::new);

        milestoneRepository.save(new Milestone(project, mileStoneRequest));
    }

    public MilestoneListResponse findMilestoneByProjectId(Long projectId) {

        List<MilestoneResponse> milestoneList =
            milestoneRepository.findMilestoneByProjectId(projectId)
                               .stream()
                               .map(MilestoneResponse::new)
                               .collect(toList());

        return new MilestoneListResponse(milestoneList);
    }

    public MilestoneResponse findMilestone(Long id) {

        MilestoneDto milestoneDto =
            milestoneRepository.findMilestoneById(id)
                               .orElseThrow(MilestoneNotFoundException::new);

        return new MilestoneResponse(milestoneDto);
    }

    @Transactional
    public void modifyMilestone(MileStoneModifyRequest mileStoneModifyRequest) {

        Milestone milestone = milestoneRepository.findById(mileStoneModifyRequest.getId())
                                                 .orElseThrow(MilestoneNotFoundException::new);

        milestone.modifyMilestone(mileStoneModifyRequest);
    }

    @Transactional
    public void deleteMilestone(Long id) {

        Milestone milestone = milestoneRepository.findById(id)
                                                 .orElseThrow(MilestoneNotFoundException::new);

        milestoneRepository.delete(milestone);
    }
}

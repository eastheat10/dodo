package com.nhnacademy.taskapi.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.taskapi.dto.projection.MilestoneDto;
import com.nhnacademy.taskapi.dto.request.milestone.MileStoneCreateRequest;
import com.nhnacademy.taskapi.dto.request.milestone.MileStoneModifyRequest;
import com.nhnacademy.taskapi.dto.response.milestone.MilestoneListResponse;
import com.nhnacademy.taskapi.entity.Milestone;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.repository.MilestoneRepository;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class MilestoneServiceTest {

    @InjectMocks
    private MilestoneService milestoneService;

    @Mock
    private MilestoneRepository milestoneRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Test
    @DisplayName("마일스톤 생성")
    void createMileStone() {

        MileStoneCreateRequest request = new MileStoneCreateRequest();
        ReflectionTestUtils.setField(request, "projectId", 1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(mock(Project.class)));
        when(milestoneRepository.save(any(Milestone.class))).thenReturn(null);

        milestoneService.createMileStone(request);

        verify(projectRepository, times(1)).findById(anyLong());
        verify(milestoneRepository, times(1)).save(any(Milestone.class));
    }

    @Test
    @DisplayName("해당 프로젝트의 마일스톤 목록 조회")
    void testFindMilestoneList() {

        when(milestoneRepository.findMilestoneByProjectId(1L)).thenReturn(new ArrayList<>());

        MilestoneListResponse milestoneByProjectId = milestoneService.findMilestoneByProjectId(1L);

        verify(milestoneRepository, times(1)).findMilestoneByProjectId(1L);
        assertThat(milestoneByProjectId).isNotNull();
    }

    @Test
    @DisplayName("마일스톤 하나 조회")
    void testFindMilestone() {

        when(milestoneRepository.findMilestoneById(1L)).thenReturn(Optional.of(mock(MilestoneDto.class)));

        milestoneService.findMilestone(1L);

        verify(milestoneRepository, times(1)).findMilestoneById(1L);
    }

    @Test
    @DisplayName("마일스톤 수정")
    void testModifyMilestone() {

        MileStoneModifyRequest request = new MileStoneModifyRequest();
        ReflectionTestUtils.setField(request, "id", 1L);

        Milestone milestone = mock(Milestone.class);

        when(milestoneRepository.findById(1L)).thenReturn(Optional.of(milestone));
        doNothing().when(milestone).modifyMilestone(request);

        milestoneService.modifyMilestone(request);

        verify(milestone, times(1)).modifyMilestone(any());
    }

    @Test
    @DisplayName("마일스톤 삭제")
    void testDeleteMilestone() {

        when(milestoneRepository.findById(1L)).thenReturn(Optional.of(mock(Milestone.class)));
        doNothing().when(milestoneRepository).delete(any(Milestone.class));

        milestoneService.deleteMilestone(1L);

        verify(milestoneRepository, times(1)).delete(any(Milestone.class));
    }
}
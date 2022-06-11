package com.nhnacademy.taskapi.service;

import static java.util.stream.Collectors.*;

import com.nhnacademy.taskapi.dto.request.project.AddProjectMemberRequest;
import com.nhnacademy.taskapi.dto.request.project.ProjectCreateRequest;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.ProjectMembers;
import com.nhnacademy.taskapi.exception.ProjectNotFoundException;
import com.nhnacademy.taskapi.repository.ProjectMembersRepository;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMembersRepository projectMembersRepository;

    @Transactional
    public void createProject(ProjectCreateRequest createRequest) {

        Project createdProject = projectRepository.save(new Project(createRequest));

        ProjectMembers projectMembers =
            new ProjectMembers(createdProject, createdProject.getId(), createdProject.getName());

        projectMembersRepository.save(projectMembers);
    }

    @Transactional
    public void addMembers(AddProjectMemberRequest addMemberRequest) {

        Project project = projectRepository.findById(addMemberRequest.getProjectId())
                                           .orElseThrow(ProjectNotFoundException::new);

        List<ProjectMembers> projectMembers =
            addMemberRequest.getMemberInfoList()
                            .stream()
                            .map(info -> new ProjectMembers(project, info))
                            .collect(toList());

        projectMembersRepository.saveAll(projectMembers);
    }

    @Transactional
    public void makeDormantProject(Long id) {

        Project project = projectRepository.findById(id)
                                           .orElseThrow(ProjectNotFoundException::new);

        project.makeDormantProject();
    }

    @Transactional
    public void makeEndProject(Long id) {

        Project project = projectRepository.findById(id)
                                           .orElseThrow(ProjectNotFoundException::new);

        project.makeEndProject();
    }
}

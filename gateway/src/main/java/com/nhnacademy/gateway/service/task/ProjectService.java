package com.nhnacademy.gateway.service.task;

import com.nhnacademy.gateway.adapter.ProjectAdapter;
import com.nhnacademy.gateway.dto.request.project.AddProjectMemberRequest;
import com.nhnacademy.gateway.dto.request.project.CreateProjectRequest;
import com.nhnacademy.gateway.dto.response.project.ProjectResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectAdapter projectAdapter;

    public void createProject(final CreateProjectRequest createRequest) {

        projectAdapter.createProject(createRequest);
    }

    public List<ProjectResponse> findProjectByUsername(final String username) {

        return projectAdapter.findProjectList(username);
    }

    public ProjectResponse findProject(final Long id) {

        return projectAdapter.findProject(id);
    }

    public void addMembers(final AddProjectMemberRequest addProjectMemberRequest) {

        projectAdapter.addMembers(addProjectMemberRequest);
    }

    public void makeDormantProject(final Long id) {

        projectAdapter.makeDormantProject(id);
    }

    public void makeEndProject(final Long id) {

        projectAdapter.makeEndProject(id);
    }
}

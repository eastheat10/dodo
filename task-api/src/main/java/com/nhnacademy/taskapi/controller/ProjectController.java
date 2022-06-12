package com.nhnacademy.taskapi.controller;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import com.nhnacademy.taskapi.dto.request.project.AddProjectMemberRequest;
import com.nhnacademy.taskapi.dto.request.project.CreateProjectRequest;
import com.nhnacademy.taskapi.dto.response.project.ProjectResponse;
import com.nhnacademy.taskapi.service.ProjectService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Void> createProject(@Valid
                                              @RequestBody CreateProjectRequest createRequest) {

        projectService.createProject(createRequest);

        return ResponseEntity.status(CREATED)
                             .build();
    }

    @PostMapping("/members")
    public ResponseEntity<Void> addMembers(@Valid
                                           @RequestBody
                                           AddProjectMemberRequest addProjectMemberRequest) {

        projectService.addMembers(addProjectMemberRequest);

        return ResponseEntity.status(CREATED)
                             .build();
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<List<ProjectResponse>> findProjectsByMemberId(@PathVariable Long memberId) {

        List<ProjectResponse> projects = projectService.findProjectByMemberId(memberId);

        return ResponseEntity.status(OK)
                             .contentType(APPLICATION_JSON)
                             .body(projects);
    }

    @PutMapping("/{id}/dormant")
    public ResponseEntity<Void> makeDormantProject(@PathVariable Long id) {

        projectService.makeDormantProject(id);

        return ResponseEntity.status(OK)
                             .build();
    }

    @PutMapping("{id}/end")
    public ResponseEntity<Void> makeEndProject(@PathVariable Long id) {

        projectService.makeEndProject(id);

        return ResponseEntity.status(OK)
                             .build();
    }
}

package com.nhnacademy.gateway.adapter;

import static com.nhnacademy.gateway.adapter.AdapterTemplate.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.*;

import com.nhnacademy.gateway.dto.request.project.AddProjectMemberRequest;
import com.nhnacademy.gateway.dto.request.project.CreateProjectRequest;
import com.nhnacademy.gateway.dto.response.project.ProjectResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProjectAdapter {

    private static final String DORMANT = "dormant";
    private static final String END = "end";
    private static final String PROJECTS = "/projects";
    private static final String REQUEST_URL = BASE_URL + PROJECTS;

    private final RestTemplate restTemplate;

    public void createProject(final CreateProjectRequest createRequest) {

        create(restTemplate, PROJECTS, createRequest);
    }

    public void addMembers(final AddProjectMemberRequest addProjectMemberRequest) {

        final String PATH = PROJECTS + "/members";

        create(restTemplate, PATH, addProjectMemberRequest);
    }

    public List<ProjectResponse> findProjectList(final String username) {

        final String PATH = PROJECTS + "/members/" + username;

        AdapterTemplate<List<ProjectResponse>> template = AdapterTemplate.of();
        return template.findAll(restTemplate, PATH);
    }

    public ProjectResponse findProject(final Long id) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(APPLICATION_JSON));

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<ProjectResponse> exchange =
            restTemplate.exchange(REQUEST_URL + "/" + id, GET, httpEntity, ProjectResponse.class);

        verifyCode(exchange.getStatusCode());

        return exchange.getBody();
    }

    public void makeDormantProject(final Long id) {

        projectStatusChange(DORMANT, id);
    }

    public void makeEndProject(final Long id) {

        projectStatusChange(END, id);
    }

    private void projectStatusChange(final String status, final Long id) {

        final String PATH = "/" + id + "/" + status;

        HttpEntity<Void> httpEntity = new HttpEntity<>(new HttpHeaders());

        ResponseEntity<Void> exchange =
            restTemplate.exchange(REQUEST_URL + PATH, PUT, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        verifyCode(exchange.getStatusCode());
    }
}

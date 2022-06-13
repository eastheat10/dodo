package com.nhnacademy.gateway.adapter;

import static com.nhnacademy.gateway.adapter.AdapterTemplate.BASE_URL;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.nhnacademy.gateway.dto.request.task.CreateTaskRequest;
import com.nhnacademy.gateway.dto.request.task.ModifyTaskRequest;
import com.nhnacademy.gateway.dto.response.task.TaskListResponse;
import com.nhnacademy.gateway.dto.response.task.TaskResponse;
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
public class TaskAdapter {

    private static final String TASKS = "/tasks";
    private static final String REQUEST_URL = BASE_URL + TASKS;

    private final RestTemplate restTemplate;

    public void createTask(CreateTaskRequest createRequest) {

        AdapterTemplate.create(restTemplate, TASKS, createRequest);
    }

    public TaskResponse findTask(Long id) {

        final String PATH = "/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(APPLICATION_JSON));

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<TaskResponse> exchange =
            restTemplate.exchange(REQUEST_URL + PATH, GET, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        AdapterTemplate.verifyCode(exchange.getStatusCode());

        return exchange.getBody();
    }

    public List<TaskListResponse> findTaskList(Long projectId) {

        final String PATH = "/project/" + projectId;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(APPLICATION_JSON));

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<List<TaskListResponse>> exchange =
            restTemplate.exchange(REQUEST_URL + PATH, GET, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        AdapterTemplate.verifyCode(exchange.getStatusCode());

        return exchange.getBody();
    }

    public void modifyTask(ModifyTaskRequest modifyRequest) {

        AdapterTemplate.modify(restTemplate, TASKS, modifyRequest);
    }

    public void deleteMilestone(Long id) {

        AdapterTemplate.delete(restTemplate, TASKS, id);
    }
}

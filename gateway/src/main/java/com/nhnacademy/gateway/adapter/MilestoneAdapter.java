package com.nhnacademy.gateway.adapter;

import static com.nhnacademy.gateway.adapter.AdapterCUDTemplate.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.nhnacademy.gateway.dto.request.milestone.CreateMileStoneRequest;
import com.nhnacademy.gateway.dto.request.milestone.ModifyMileStoneRequest;
import com.nhnacademy.gateway.dto.response.milestone.MilestoneResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MilestoneAdapter {

    private static final String MILESTONES = "/milestones";
    private static final String REQUEST_URL = BASE_URL + MILESTONES;

    private final RestTemplate restTemplate;

    public void createMilestone(CreateMileStoneRequest createRequest) {

        create(restTemplate, MILESTONES, createRequest);
    }

    public MilestoneResponse findMilestone(Long id) {

        final String PATH = "/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(APPLICATION_JSON));

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<MilestoneResponse> exchange =
            restTemplate.exchange(REQUEST_URL + PATH, GET, httpEntity, MilestoneResponse.class);

        verifyCode(exchange.getStatusCode());

        return exchange.getBody();
    }

    public void modifyMilestone(ModifyMileStoneRequest modifyRequest) {

        modify(restTemplate, MILESTONES, modifyRequest);
    }

    public void deleteMilestone(Long id) {

        delete(restTemplate, MILESTONES, id);
    }
}
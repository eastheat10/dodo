package com.nhnacademy.gateway.adapter;

import static com.nhnacademy.gateway.adapter.AdapterTemplate.BASE_URL;
import static com.nhnacademy.gateway.adapter.AdapterTemplate.verifyCode;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.nhnacademy.gateway.dto.request.tag.CreateTagRequest;
import com.nhnacademy.gateway.dto.request.tag.ModifyTagRequest;
import com.nhnacademy.gateway.dto.response.tag.TagResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class TagAdapter {

    private static final String TAGS = "/tags";
    private static final String REQUEST_URL = BASE_URL + TAGS;

    private final RestTemplate restTemplate;

    public void createTag(CreateTagRequest createRequest) {

        AdapterTemplate.create(restTemplate, TAGS, createRequest);
    }

    public List<TagResponse> findTagsByProjectId(Long projectId) {

        AdapterTemplate<List<TagResponse>> template = AdapterTemplate.of();
        return template.findAll(restTemplate, TAGS + "/project/" + projectId);
    }

    public TagResponse findById(Long id) {

        final String PATH = "/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(APPLICATION_JSON));

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<TagResponse> exchange =
            restTemplate.exchange(REQUEST_URL + PATH, GET, httpEntity, TagResponse.class);

        verifyCode(exchange.getStatusCode());

        return exchange.getBody();
    }


    public void modifyTag(ModifyTagRequest modifyRequest) {

        AdapterTemplate.modify(restTemplate, TAGS, modifyRequest);
    }

    public void deleteTag(Long id) {

        AdapterTemplate.delete(restTemplate, TAGS, id);
    }

}

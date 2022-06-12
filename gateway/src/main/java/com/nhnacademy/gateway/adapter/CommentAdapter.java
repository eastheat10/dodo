package com.nhnacademy.gateway.adapter;

import static com.nhnacademy.gateway.adapter.AdapterCUDTemplate.*;

import com.nhnacademy.gateway.dto.request.comment.CreateCommentRequest;
import com.nhnacademy.gateway.dto.request.comment.ModifyCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CommentAdapter {

    private static final String COMMENT = "/comments";

    private final RestTemplate restTemplate;

    public void addComment(CreateCommentRequest createRequest) {

        create(restTemplate, COMMENT, createRequest);
    }

    public void modifyComment(ModifyCommentRequest modifyRequest) {

       modify(restTemplate, COMMENT, modifyRequest);
    }

    public void deleteComment(Long id) {

        delete(restTemplate, COMMENT, id);
    }
}

package com.nhnacademy.gateway.service;

import com.nhnacademy.gateway.adapter.CommentAdapter;
import com.nhnacademy.gateway.dto.request.comment.CreateCommentRequest;
import com.nhnacademy.gateway.dto.request.comment.ModifyCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentAdapter commentAdapter;

    public void addComment(CreateCommentRequest createRequest) {

        commentAdapter.addComment(createRequest);
    }

    public void modifyComment(ModifyCommentRequest modifyRequest) {

        commentAdapter.modifyComment(modifyRequest);
    }

    public void delete(Long id) {

        commentAdapter.deleteComment(id);
    }
}

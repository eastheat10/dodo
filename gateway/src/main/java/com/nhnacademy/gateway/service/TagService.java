package com.nhnacademy.gateway.service;

import com.nhnacademy.gateway.adapter.TagAdapter;
import com.nhnacademy.gateway.dto.request.tag.CreateTagRequest;
import com.nhnacademy.gateway.dto.request.tag.ModifyTagRequest;
import com.nhnacademy.gateway.dto.response.tag.TagResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagAdapter tagAdapter;

    public void createTag(CreateTagRequest createRequest) {

        tagAdapter.createTag(createRequest);
    }

    public List<TagResponse> findTagsByProjectId(Long projectId) {

        return tagAdapter.findTagsByProjectId(projectId);
    }

    public TagResponse findTag(Long id) {

        return tagAdapter.findById(id);
    }

    public void modifyTag(ModifyTagRequest modifyRequest) {

        tagAdapter.modifyTag(modifyRequest);
    }

    public void deleteTag(Long id) {

        tagAdapter.deleteTag(id);
    }
}

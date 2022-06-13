package com.nhnacademy.gateway.dto.response.task;

import com.nhnacademy.gateway.dto.response.milestone.MilestoneResponse;
import com.nhnacademy.gateway.dto.response.tag.TagResponse;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaskResponse {

    private Long id;
    private String title;
    private String content;
    private String registrantName;
    private MilestoneResponse milestone;
    private List<TagResponse> tags;
    private List<PersonResponse> persons;
    private List<CommentResponse> comments;
}

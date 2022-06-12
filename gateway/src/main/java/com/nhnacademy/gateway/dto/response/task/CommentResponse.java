package com.nhnacademy.gateway.dto.response.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private Long id;
    private String username;
    private String comment;

    @JsonFormat(pattern = "yyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyy-MM-dd HH:mm")
    private LocalDateTime modifiedAt;
}
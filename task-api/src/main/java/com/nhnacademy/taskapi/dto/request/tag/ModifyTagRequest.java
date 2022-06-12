package com.nhnacademy.taskapi.dto.request.tag;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyTagRequest {

    private Long id;
    private String tagName;
}

package com.nhnacademy.taskapi.dto.request.project;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddProjectMemberRequest {

    private Long projectId;
    private List<MemberInfo> memberInfoList = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    public static class MemberInfo {
        private Long memberId;
        private String username;
    }
}

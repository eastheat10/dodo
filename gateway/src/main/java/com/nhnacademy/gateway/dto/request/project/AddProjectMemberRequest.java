package com.nhnacademy.gateway.dto.request.project;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddProjectMemberRequest {

    private Long projectId;
    private List<MemberInfo> memberInfoList;

    @Getter
    @AllArgsConstructor
    public static class MemberInfo {
        private Long memberId;
        private String username;
    }
}


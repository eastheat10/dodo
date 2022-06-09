package com.nhnacademy.taskapi.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Project_Members")
@NoArgsConstructor
public class ProjectMembers {

    @EmbeddedId
    private Pk id;

    @MapsId("projectId")
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project projects;

    private String username;

    @Getter
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        private Long projectId;

        @Column(name = "member_id")
        private Long memberId;
    }
}

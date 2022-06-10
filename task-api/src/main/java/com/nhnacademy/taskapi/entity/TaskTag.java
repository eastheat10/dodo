package com.nhnacademy.taskapi.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Task_Tags")
@NoArgsConstructor
public class TaskTag {

    @EmbeddedId
    private Pk id;

    @MapsId("taskId")
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @MapsId("tagId")
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    public static class Pk implements Serializable {

        private Long taskId;
        private Long tagId;
    }
}

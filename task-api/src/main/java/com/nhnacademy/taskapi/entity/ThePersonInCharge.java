package com.nhnacademy.taskapi.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "The_person_in_charge")
@NoArgsConstructor
public class ThePersonInCharge {

    @EmbeddedId
    private Pk id;

    @MapsId("taskId")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "task_id")
    private Task task;

    public ThePersonInCharge(Task task, Long memberId) {
        this.id = new Pk(memberId, task.getId());
        this.task = task;
    }

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pk implements Serializable {

        private Long taskId;

        @Column(name = "member_id")
        private Long memberId;
    }
}

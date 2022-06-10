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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "The_person_in_charge")
@NoArgsConstructor
public class ThePersonInCharge {

    @EmbeddedId
    private Pk id;

    @MapsId("taskId")
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    public static class Pk implements Serializable {

        @Column(name = "member_id")
        private Long member_id;

        private Long taskId;
    }
}

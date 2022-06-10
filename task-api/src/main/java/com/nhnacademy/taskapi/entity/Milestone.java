package com.nhnacademy.taskapi.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Milestones")
@NoArgsConstructor
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private String name;

    @Column(name = "start_data")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}

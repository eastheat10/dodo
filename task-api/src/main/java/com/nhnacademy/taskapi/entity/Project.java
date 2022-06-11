package com.nhnacademy.taskapi.entity;

import com.nhnacademy.taskapi.dto.request.project.ProjectCreateRequest;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Projects")
@Getter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(name = "admin_id")
    private Long adminId;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public Project(ProjectCreateRequest request) {
        this.adminId = request.getAdminId();
        this.status = ProjectStatus.PROGRESS;
        this.name = request.getProjectName();
        this.startDate = LocalDate.now();
    }

    public void makeEndProject() {
        this.status = ProjectStatus.END;
    }

    public void makeDormantProject() {
        this.status = ProjectStatus.DORMANT;
    }
}

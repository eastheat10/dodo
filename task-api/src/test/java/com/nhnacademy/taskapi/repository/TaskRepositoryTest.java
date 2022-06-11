package com.nhnacademy.taskapi.repository;

import static org.assertj.core.api.Assertions.*;

import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.ProjectStatus;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.exception.TaskNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    @DisplayName("프로젝트 아이디로 업무 조회")
    void testFindTaskByProjectId() {

        Project project = new Project();

        ReflectionTestUtils.setField(project, "adminId", 99L);
        ReflectionTestUtils.setField(project, "status", ProjectStatus.PROGRESS);
        ReflectionTestUtils.setField(project, "name", "project name");
        ReflectionTestUtils.setField(project, "startDate", LocalDate.now());

        Project savedProject = projectRepository.save(project);

        Task task = new Task();
        ReflectionTestUtils.setField(task, "project", savedProject);
        ReflectionTestUtils.setField(task, "title", "title");
        ReflectionTestUtils.setField(task, "content", "content");
        ReflectionTestUtils.setField(task, "registrantName", "registrantName");
        ReflectionTestUtils.setField(task, "createdAt", LocalDateTime.now());

        Task savedTask = taskRepository.saveAndFlush(task);

        Task findTask = taskRepository.findByProject_Id(savedProject.getId())
                                   .orElseThrow(TaskNotFoundException::new);

        assertThat(findTask.getProject().getId()).isEqualTo(savedProject.getId());

    }

}
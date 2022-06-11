package com.nhnacademy.taskapi.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.nhnacademy.taskapi.dto.request.task.CreateTaskRequest;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.repository.MilestoneRepository;
import com.nhnacademy.taskapi.repository.PersonRepository;
import com.nhnacademy.taskapi.repository.ProjectMembersRepository;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.repository.TagRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import com.nhnacademy.taskapi.repository.TaskTagRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private MilestoneRepository milestoneRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TaskTagRepository taskTagRepository;
    @Mock
    private ProjectMembersRepository projectMembersRepository;
    @Mock
    private PersonRepository personRepository;


    @Test
    @DisplayName("업무 생성")
    void testCreateTask() {

        CreateTaskRequest request = new CreateTaskRequest();

        ReflectionTestUtils.setField(request, "projectId", 1L);
        ReflectionTestUtils.setField(request, "milestoneId", 0L);
        ReflectionTestUtils.setField(request, "tags", new ArrayList<>());
        ReflectionTestUtils.setField(request, "persons", new ArrayList<>());

        Task task = mock(Task.class);

        when(projectRepository.findById(request.getProjectId())).thenReturn(
            Optional.of(mock(Project.class)));
        lenient().when(tagRepository.findById(anyLong())).thenReturn(null);
        lenient().when(projectMembersRepository.existsById_memberId(anyLong())).thenReturn(false);

        lenient().when(task.getId()).thenReturn(1L);

        taskService.createTask(request);
    }

    @Test
    @DisplayName("해당 프로젝트의 업무 조회")
    void findTaskByProjectId() {

        long taskId = 1L;
        Task task = new Task();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
    }
}
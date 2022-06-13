package com.nhnacademy.gateway.service.task;

import com.nhnacademy.gateway.adapter.TaskAdapter;
import com.nhnacademy.gateway.dto.request.task.CreateTaskRequest;
import com.nhnacademy.gateway.dto.request.task.ModifyTaskRequest;
import com.nhnacademy.gateway.dto.response.task.TaskListResponse;
import com.nhnacademy.gateway.dto.response.task.TaskResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskAdapter taskAdapter;

    public void createTask(final CreateTaskRequest createRequest) {

        taskAdapter.createTask(createRequest);
    }

    public List<TaskListResponse> findTasksByProjectId(final Long projectId) {

        return taskAdapter.findTaskList(projectId);
    }

    public TaskResponse findTask(final Long id) {

        return taskAdapter.findTask(id);
    }

    public void modifyTask(final ModifyTaskRequest modifyRequest) {

        taskAdapter.modifyTask(modifyRequest);
    }

    public void deleteTask(final Long id) {

        taskAdapter.deleteTask(id);
    }
}

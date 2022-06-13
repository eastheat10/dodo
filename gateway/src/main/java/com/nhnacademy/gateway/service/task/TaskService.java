package com.nhnacademy.gateway.service.task;

import com.nhnacademy.gateway.adapter.TaskAdapter;
import com.nhnacademy.gateway.dto.response.task.TaskListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskAdapter taskAdapter;

    public List<TaskListResponse> findTaskList(final Long projectId) {

        return taskAdapter.findTaskList(projectId);
    }
}

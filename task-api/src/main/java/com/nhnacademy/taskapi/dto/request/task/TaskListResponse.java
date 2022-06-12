package com.nhnacademy.taskapi.dto.request.task;

import com.nhnacademy.taskapi.entity.Task;
import lombok.Getter;

@Getter
public class TaskListResponse {

    private final Long id;
    private final String title;

    public TaskListResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
    }
}

package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTag.Pk> {
}

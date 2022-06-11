package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Task;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByProject_Id(Long projectId);
}

package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.ProjectMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMembersRepository extends JpaRepository<ProjectMembers, ProjectMembers.Pk> {
}

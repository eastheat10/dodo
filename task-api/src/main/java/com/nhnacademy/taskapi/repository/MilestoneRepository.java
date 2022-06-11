package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.dto.projection.MilestoneDto;
import com.nhnacademy.taskapi.entity.Milestone;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

    @Query("SELECT m.id AS id, " +
        "           p.id AS projectId, " +
        "           m.name AS name, " +
        "           m.startDate AS startDate," +
        "           m.endDate AS endDate " +
        "FROM Milestone m " +
        "   INNER JOIN Project p " +
        "       ON m.project.id = p.id " +
        "WHERE p.id = :projectId")
    List<MilestoneDto> findMilestoneByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT m.id AS id, " +
        "           p.id AS projectId, " +
        "           m.name AS name, " +
        "           m.startDate AS startDate," +
        "           m.endDate AS endDate " +
        "FROM Milestone m " +
        "   INNER JOIN Project p " +
        "       ON m.project.id = p.id " +
        "WHERE m.id = :id")
    Optional<MilestoneDto> findMilestoneById(@Param("id") Long id);
}

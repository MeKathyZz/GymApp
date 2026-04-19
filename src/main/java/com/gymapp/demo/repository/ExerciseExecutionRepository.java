package com.gymapp.demo.repository;

import com.gymapp.demo.model.ExerciseExecution;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ExerciseExecutionRepository extends JpaRepository<ExerciseExecution, Long> {

    @Query("SELECT ee FROM ExerciseExecution ee " +
            "WHERE ee.exercise.id = :exerciseId " +
            "ORDER BY ee.createdAt DESC")
    List<ExerciseExecution> findRecentExecutions(@Param("exerciseId") Long exerciseId, Pageable pageable);

    @Query("SELECT MAX(ee.weightUsed) FROM ExerciseExecution ee WHERE ee.exercise.id = :exerciseId")
    Double findMaxWeightByExerciseId(@Param("exerciseId") Long exerciseId);

    List<ExerciseExecution> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
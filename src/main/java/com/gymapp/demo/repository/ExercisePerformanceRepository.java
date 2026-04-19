package com.gymapp.demo.repository;

import com.gymapp.demo.model.ExercisePerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExercisePerformanceRepository extends JpaRepository<ExercisePerformance, Long> {

    @Query("SELECT MAX(ep.weightUsed) FROM ExercisePerformance ep WHERE ep.exercise.id = :exerciseId")
    Double findMaxWeightByExerciseId(@Param("exerciseId") Long exerciseId);
}
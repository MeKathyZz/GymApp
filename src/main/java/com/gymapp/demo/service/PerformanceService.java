package com.gymapp.demo.service;

import com.gymapp.demo.dto.ExerciseTargetDTO;
import com.gymapp.demo.model.Exercise;
import com.gymapp.demo.model.ExercisePerformance;
import com.gymapp.demo.repository.ExercisePerformanceRepository;
import com.gymapp.demo.repository.ExerciseRepository;
import org.springframework.stereotype.Service;

@Service
public class PerformanceService {

    private final ExerciseRepository exerciseRepository;
    private final ExercisePerformanceRepository performanceRepository;

    public PerformanceService(ExerciseRepository exerciseRepository,
                              ExercisePerformanceRepository performanceRepository) {
        this.exerciseRepository = exerciseRepository;
        this.performanceRepository = performanceRepository;
    }

    public ExerciseTargetDTO getExerciseWithPR(Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercício não encontrado"));

        Double pr = performanceRepository.findMaxWeightByExerciseId(exerciseId);

        return new ExerciseTargetDTO(
                exercise.getName(),
                pr != null ? pr : 0.0,
                exercise.getTargetSets(),
                exercise.getTargetReps(),
                exercise.getTargetTime(),
                exercise.isIsometric()
        );
    }

    public ExercisePerformance savePerformance(ExercisePerformance performance) {
        return performanceRepository.save(performance);
    }
}
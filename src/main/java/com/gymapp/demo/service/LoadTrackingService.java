package com.gymapp.demo.service;

import com.gymapp.demo.model.ExerciseExecution;
import com.gymapp.demo.repository.ExerciseExecutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoadTrackingService {

    @Autowired
    private ExerciseExecutionRepository repository;

    /**
     * Calculates the total volume of a single exercise performance.
     * Formula: Sets * Reps * Weight
     */
    public Double calculateVolume(ExerciseExecution execution) {
        if (execution.getWeightUsed() == null || execution.getRepsDone() == null || execution.getActualSets() == null) {
            return 0.0;
        }
        return execution.getWeightUsed() * execution.getRepsDone() * execution.getActualSets();
    }

    /**
     * Compares the current performance against the historical personal record (PR).
     */
    public Boolean isNewPersonalRecord(ExerciseExecution current) {
        Double maxWeight = repository.findMaxWeightByExerciseId(current.getExercise().getId());

        if (maxWeight == null) return true; // First time is always a PR

        return current.getWeightUsed() > maxWeight;
    }

    /**
     * Returns the "Load Trend" compared to the last time this exercise was performed.
     */
    public String getLoadTrend(ExerciseExecution current) {
        Optional<ExerciseExecution> lastExecution = repository
                .findRecentExecutions(current.getExercise().getId(), org.springframework.data.domain.PageRequest.of(0, 1))
                .stream().findFirst();

        if (lastExecution.isEmpty()) return "NEW_EXERCISE";

        double currentVol = calculateVolume(current);
        double lastVol = calculateVolume(lastExecution.get());

        if (currentVol > lastVol) return "INCREASING";
        if (currentVol < lastVol) return "DECREASING";
        return "STABLE";
    }
}
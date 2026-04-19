package com.gymapp.demo.service;

import com.gymapp.demo.dto.WorkoutSessionDTO;
import com.gymapp.demo.model.ExerciseExecution;
import com.gymapp.demo.repository.ExerciseExecutionRepository;
import com.gymapp.demo.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutionService {

    @Autowired
    private ExerciseExecutionRepository executionRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    public void saveExecution(WorkoutSessionDTO data) {
        ExerciseExecution execution = new ExerciseExecution();

        var exercise = exerciseRepository.findById(data.getExerciseId())
                .orElseThrow(() -> new RuntimeException("Exercício não encontrado"));

        execution.setExercise(exercise);
        execution.setWeightUsed(data.getWeightUsed());
        execution.setRepsDone(data.getRepsDone());
        execution.setSetsDone(data.getSetsDone());
        execution.setTimeAchieved(data.getTimeAchieved());
        execution.setNotes(data.getNote());

        executionRepository.save(execution);
    }
}
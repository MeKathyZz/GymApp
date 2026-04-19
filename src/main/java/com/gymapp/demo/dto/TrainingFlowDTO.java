package com.gymapp.demo.dto;

import com.gymapp.demo.model.Exercise;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TrainingFlowDTO {
    private String trainingName;
    private boolean mobilityUnlocked;
    private boolean strengthUnlocked;
    private List<Exercise> availableExercises;
    private String message;
}

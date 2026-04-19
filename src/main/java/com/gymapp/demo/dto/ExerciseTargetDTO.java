package com.gymapp.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class ExerciseTargetDTO {
    private String exerciseName;
    private Double personalRecord;
    private Integer targetSets;
    private Integer targetReps;
    private Integer targetTime;
    private boolean isIsometric;
}
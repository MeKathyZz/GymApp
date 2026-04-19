package com.gymapp.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSummaryDTO {

    private Long executionId;
    private String trainingName;
    private Double totalVolume;

    private Map<String, Double> volumeByExercise;
}
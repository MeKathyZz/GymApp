package com.gymapp.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class WeeklySummaryDTO {
    private Double currentWeeklyVolume;
    private Double pastWeeklyVolume;
    private Double evolutionPercentage;
    private String evolutionMessage;
    private Integer workoutsDone;
    private String motivationMessage;
}
package com.gymapp.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkoutSessionDTO {
    private Long exerciseId;
    private Integer setsDone;
    private Integer repsDone;
    private Double weightUsed;
    private Integer timeAchieved;
    private String note;
}
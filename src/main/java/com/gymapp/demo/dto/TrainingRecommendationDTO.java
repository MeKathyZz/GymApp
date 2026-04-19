package com.gymapp.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class TrainingRecommendationDTO {
    private String status;
    private String message;
    private String action;
}
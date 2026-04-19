package com.gymapp.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeeklyReportDTO {
    private Double totalWeeklyVolume;
    private String fatigueStatus;
    private String mostSoreMuscle;
    private String recommendation;
}
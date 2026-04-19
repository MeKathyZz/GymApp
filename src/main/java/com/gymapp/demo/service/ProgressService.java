package com.gymapp.demo.service;

import com.gymapp.demo.model.ExerciseExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {

    @Autowired
    private LoadTrackingService loadTrackingService;

    public String checkProgression(ExerciseExecution current) {

        // 1. Verifica Recorde Pessoal
        if (loadTrackingService.isNewPersonalRecord(current)) {
            return "🔥 NEW PERSONAL RECORD! You've never lifted " + current.getWeightUsed() + "kg before!";
        }

        // 2. Verifica a Tendência de Volume
        String trend = loadTrackingService.getLoadTrend(current);

        return switch (trend) {
            case "INCREASING" -> "📈 Progressive Overload: Your total volume increased since last session!";
            case "DECREASING" -> "⚠️ Recovery Mode: Volume was lower today. Focus on form!";
            case "STABLE" -> "⚖️ Consistency: You maintained the same workload. Solid effort!";
            default -> "🚀 Baseline set: First log for this exercise!";
        };
    }
}
package com.gymapp.demo.dto;

import com.gymapp.demo.model.Exercise;
import java.util.List;
import java.util.Map;

public record TrainingResponseDTO(
        Long id,
        String name,
        List<Exercise> exercises,
        Map<Long, String> lastPerformances
) {}
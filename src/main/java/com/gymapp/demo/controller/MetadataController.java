package com.gymapp.demo.controller;

import com.gymapp.demo.model.ExerciseType;
import com.gymapp.demo.model.MuscleGroup;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

    @GetMapping("/muscle-groups")
    public List<String> getMuscleGroups() {
        return Arrays.stream(MuscleGroup.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @GetMapping("/exercise-types")
    public List<String> getExerciseTypes() {
        return Arrays.stream(ExerciseType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
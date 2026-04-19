package com.gymapp.demo.controller;

import com.gymapp.demo.dto.ExerciseTargetDTO;
import com.gymapp.demo.model.ExercisePerformance;
import com.gymapp.demo.service.PerformanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/performance")
public class PerformanceController {

    private final PerformanceService service;

    public PerformanceController(PerformanceService service) {
        this.service = service;
    }

    @GetMapping("/target/{exerciseId}")
    public ResponseEntity<ExerciseTargetDTO> getTarget(@PathVariable Long exerciseId) {
        return ResponseEntity.ok(service.getExerciseWithPR(exerciseId));
    }

    @PostMapping
    public ResponseEntity<ExercisePerformance> save(@RequestBody ExercisePerformance performance) {
        return ResponseEntity.ok(service.savePerformance(performance));
    }
}
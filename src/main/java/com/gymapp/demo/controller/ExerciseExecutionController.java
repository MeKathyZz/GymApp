package com.gymapp.demo.controller;

import com.gymapp.demo.model.ExerciseExecution;
import com.gymapp.demo.service.ExerciseExecutionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/executions")
public class ExerciseExecutionController {

    private final ExerciseExecutionService service;

    public ExerciseExecutionController(ExerciseExecutionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ExerciseExecution> create(@Valid @RequestBody ExerciseExecution execution) {
        return ResponseEntity.ok(service.saveExecution(execution));
    }

    @GetMapping
    public List<ExerciseExecution> getAll() {
        return service.listAll();
    }

    // Relatório de tipos de treino (Musculação, Calistenia, Core)
    @GetMapping("/report/types")
    public ResponseEntity<Map<String, Long>> getReport(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(service.getMonthlyReport(month, year));
    }

    // Contador de dias treinados
    @GetMapping("/report/days")
    public ResponseEntity<Long> getTrainingDays(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(service.countTrainingDaysInMonth(month, year));
    }
}
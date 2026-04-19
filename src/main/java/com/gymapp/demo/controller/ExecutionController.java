package com.gymapp.demo.controller;

import com.gymapp.demo.dto.PersonalRecordDTO;
import com.gymapp.demo.dto.WeeklySummaryDTO;
import com.gymapp.demo.dto.WorkoutSessionDTO;
import com.gymapp.demo.dto.WorkoutSummaryDTO;
import com.gymapp.demo.model.Execution;
import com.gymapp.demo.repository.ExecutionRepository;
import com.gymapp.demo.service.ExecutionService;
import com.gymapp.demo.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/executions")
public class ExecutionController {

    private final ExecutionService executionService;
    private final ExecutionRepository repository;
    private final ProgressService progressService;

    public ExecutionController(ExecutionService executionService,
                               ExecutionRepository repository,
                               ProgressService progressService) {
        this.executionService = executionService;
        this.repository = repository;
        this.progressService = progressService;
    }

    @PostMapping("/session")
    public ResponseEntity<Void> recordExecution(@RequestBody WorkoutSessionDTO data) {
        executionService.saveExecution(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveExecution(@RequestBody Execution execution) {
        Execution saved = repository.save(execution);

        List<String> insights = saved.getExerciseDetails().stream()
                .map(progressService::checkProgression)
                .toList();

        return ResponseEntity.ok(Map.of(
                "message", "Execution saved successfully!",
                "insights", insights,
                "data", saved
        ));
    }

    @GetMapping
    public List<Execution> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}/volume")
    public WorkoutSummaryDTO getWorkoutVolume(@PathVariable Long id) {
        Execution execution = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado"));

        Map<String, Double> volumeByExercise = execution.getExerciseDetails().stream()
                .filter(ed -> ed.getWeightUsed() != null && ed.getRepsDone() != null)
                .collect(Collectors.groupingBy(
                        ed -> ed.getExercise().getName(),
                        Collectors.summingDouble(ed -> ed.getWeightUsed() * ed.getRepsDone())
                ));

        double totalVolume = volumeByExercise.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        return new WorkoutSummaryDTO(
                execution.getId(),
                execution.getTraining().getName(),
                totalVolume,
                volumeByExercise
        );
    }

    @GetMapping("/weekly-summary")
    public ResponseEntity<WeeklySummaryDTO> getWeeklySummary() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastSunday = now.minusDays(7);
        LocalDateTime twoWeeksAgo = now.minusDays(14);

        List<Execution> currentExecutions = repository.findAllByTimestampAfter(lastSunday);
        double currentVolume = calculateTotalVolume(currentExecutions);

        List<Execution> pastExecutions = repository.findAllByTimestampBetween(twoWeeksAgo, lastSunday);
        double pastVolume = calculateTotalVolume(pastExecutions);

        WeeklySummaryDTO summary = buildWeeklySummary(currentVolume, pastVolume, currentExecutions.size());

        return ResponseEntity.ok(summary);
    }

    private WeeklySummaryDTO buildWeeklySummary(double currentVolume, double pastVolume, int workoutsDone) {
        double diffPercentage = 0.0;
        if (pastVolume > 0) {
            diffPercentage = ((currentVolume - pastVolume) / pastVolume) * 100;
        }

        String evolutionMsg = (pastVolume > 0)
                ? String.format("Evolução de %.1f%% em relação à semana passada!", diffPercentage)
                : "Primeira semana registrada. O objetivo agora é superar essa marca!";

        return new WeeklySummaryDTO(
                currentVolume,
                pastVolume,
                diffPercentage,
                evolutionMsg,
                workoutsDone,
                "Essa semana tu moveu " + currentVolume + "kg. " + evolutionMsg
        );
    }

    private double calculateTotalVolume(List<Execution> executions) {
        return executions.stream()
                .flatMap(e -> e.getExerciseDetails().stream())
                .filter(ed -> ed.getWeightUsed() != null && ed.getRepsDone() != null)
                .mapToDouble(ed -> ed.getWeightUsed() * ed.getRepsDone())
                .sum();
    }

    @GetMapping("/records")
    public List<PersonalRecordDTO> getPersonalRecords() {
        return repository.findPersonalRecords();
    }
}
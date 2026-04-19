package com.gymapp.demo.service;

import com.gymapp.demo.model.ExerciseExecution;
import com.gymapp.demo.repository.ExerciseExecutionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExerciseExecutionService {

    public Map<String, Long> getMonthlyReport(int month, int year) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1).minusNanos(1);

        List<ExerciseExecution> executions = repository.findByCreatedAtBetween(start, end);

        // Agrupa por tipo de exercício (Musculação, Calistenia, Core) e conta quantos foram feitos
        return executions.stream()
                .filter(e -> e.getExercise() != null && e.getExercise().getType() != null)
                .collect(Collectors.groupingBy(
                        e -> e.getExercise().getType().toString(),
                        Collectors.counting()
                ));
    }

    public long countTrainingDaysInMonth(int month, int year) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1).minusNanos(1);

        List<ExerciseExecution> executions = repository.findByCreatedAtBetween(start, end);

        // Conta quantos dias únicos tiveram pelo menos uma execução
        return executions.stream()
                .map(e -> e.getCreatedAt().toLocalDate())
                .distinct()
                .count();
    }

    private final ExerciseExecutionRepository repository;

    public ExerciseExecutionService(ExerciseExecutionRepository repository) {
        this.repository = repository;
    }

    public ExerciseExecution saveExecution(ExerciseExecution execution) {
        return repository.save(execution);
    }

    public List<ExerciseExecution> listAll() {
        return repository.findAll();
    }
}
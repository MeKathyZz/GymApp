package com.gymapp.demo.service;

import com.gymapp.demo.dto.CoreResponseDTO;
import com.gymapp.demo.dto.TrainingFlowDTO;
import com.gymapp.demo.dto.TrainingResponseDTO;
import com.gymapp.demo.model.Training;
import com.gymapp.demo.model.Exercise;
import com.gymapp.demo.repository.ExerciseExecutionRepository;
import com.gymapp.demo.repository.TrainingRepository;
import com.gymapp.demo.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TrainingService {

    @Autowired
    private TrainingRepository repository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseExecutionRepository executionRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void resetMobilityStatus() {
        List<Training> allTrainings = repository.findAll();
        allTrainings.forEach(t -> t.setMobilityCompleted(false));
        repository.saveAll(allTrainings);
        System.out.println("Status de mobilidade resetado para todos os treinos.");
    }

    public CoreResponseDTO getDailyCore() {
        var dayOfWeek = LocalDate.now().getDayOfWeek();

        String intensity = (dayOfWeek.getValue() % 2 != 0) ? "HOT" : "LIGHT";

        List<Exercise> allCore = exerciseRepository.findByMuscleGroup(Exercise.MuscleGroup.CORE);

        List<Exercise> filtered = allCore.stream()
                .filter(ex -> intensity.equals("HOT") == ex.isIsometric())
                .collect(Collectors.toList());

        String msg = intensity.equals("HOT") ? "Dia de intensidade máxima no core!" : "Recuperação ativa: Core leve.";

        return new CoreResponseDTO(intensity, filtered, msg);
    }

    public TrainingResponseDTO getTodayTraining(String mode) {
        var today = LocalDate.now().getDayOfWeek();

        Training training = repository.findByScheduledDaysContains(today)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado para hoje!"));

        List<Exercise> filtered = filterByMode(training.getExercises(), mode);

        Map<Long, String> lastPerformances = filtered.stream().collect(Collectors.toMap(
                Exercise::getId,
                ex -> executionRepository.findRecentExecutions(
                                ex.getId(),
                                PageRequest.of(0, 1)
                        ).stream()
                        .findFirst()
                        .map(last -> last.getWeightUsed() + "kg x " + last.getRepsDone())
                        .orElse("No history yet")
        ));

        return new TrainingResponseDTO(
                training.getId(),
                training.getName(),
                filtered,
                lastPerformances
        );
    }

    private List<Exercise> filterByMode(List<Exercise> exercises, String mode) {
        List<Exercise> filtered = new ArrayList<>();

        if ("SOFT".equalsIgnoreCase(mode)) {
            for (Exercise ex : exercises) {
                if (Boolean.TRUE.equals(ex.getHomeCompatible())) {
                    filtered.add(ex);
                }
            }
        } else if ("BREAK".equalsIgnoreCase(mode)) {
            for (Exercise ex : exercises) {
                if (ex.getType() != null &&
                        (ex.getType().name().equals("MOBILITY") || ex.getType().name().equals("CORE"))) {
                    filtered.add(ex);
                }
            }
        } else {
            filtered.addAll(exercises);
        }

        return filtered;
    }

    public TrainingFlowDTO getTrainingFlow(Long trainingId) {
        Training training = repository.findById(trainingId).orElseThrow();

        if (!training.isMobilityCompleted()) {
            return new TrainingFlowDTO(
                    training.getName(),
                    true,
                    false,
                    training.getMobilityExercises(),
                    "Realize a mobilidade para liberar o treino de força."
            );
        }

        return new TrainingFlowDTO(
                training.getName(),
                true,
                true,
                training.getStrengthExercises(),
                "Mobilidade concluída. Bom treino de força!"
        );
    }

    public void completeMobility(Long id) {
        Training training = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado!"));

        training.setMobilityCompleted(true);
        repository.save(training);
    }
}
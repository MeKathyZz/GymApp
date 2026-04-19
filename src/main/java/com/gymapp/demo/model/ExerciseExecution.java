package com.gymapp.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import jakarta.persistence.PrePersist;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseExecution {

    private LocalDateTime createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean mobilityDone = false;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private Integer actualReps;
    private Integer actualSeconds;
    private Integer setNumber;
    private Integer setsDone;
    private Integer rpe;
    private Integer timeAchieved;
    private String notes;

    @PositiveOrZero(message = "O peso não pode ser negativo!")
    private Double weightUsed;

    @Min(value = 1, message = "Você precisa fazer pelo menos 1 repetição")
    private Integer repsDone;

    @Min(value = 1, message = "O treino deve ter pelo menos 1 série")
    private Integer actualSets;
}

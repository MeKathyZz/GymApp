package com.gymapp.demo.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean homeCompatible;

    @Enumerated(EnumType.STRING)
    private MuscleGroup muscleGroup;

    private String category;

    @Enumerated(EnumType.STRING)
    private ExerciseType type;

    private Integer targetSets;
    private Integer targetTime;

    private boolean isIsometric = false;

    public enum MuscleGroup {
        CHEST, BACK, LEGS, SHOULDERS, BICEPS, TRICEPS, CORE, MOBILITY
    }

    public enum ExerciseType {
        MOBILITY, STRENGTH, SKILL, CORE
    }

    @NotBlank(message = "O nome do exercício é obrigatório")
    private String name;

    @Positive(message = "A meta de repetições deve ser maior que zero")
    private Integer targetReps;
}
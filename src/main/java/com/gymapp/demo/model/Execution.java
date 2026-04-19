package com.gymapp.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Execution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate date = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private MuscleGroup muscleGroup;

    @Enumerated(EnumType.STRING)
    private ExerciseType type;

    private Integer targetSets;
    private Integer targetReps;
    private Integer targetTime;
    private boolean isIsometric;
    private Boolean homeCompatible;

    @CreationTimestamp
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    private Training training;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "execution_id")
    private List<ExerciseExecution> exerciseDetails;

}
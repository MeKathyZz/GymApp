package com.gymapp.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "training_days", joinColumns = @JoinColumn(name = "training_id"))
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> scheduledDays = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "training_mobility")
    private List<Exercise> mobilityExercises;

    @ManyToMany
    @JoinTable(name = "training_strength")
    private List<Exercise> strengthExercises;

    private boolean mobilityCompleted = false;

    @ManyToMany
    @JoinTable(
            name = "training_exercises",
            joinColumns = @JoinColumn(name = "training_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private List<Exercise> exercises = new ArrayList<>();

}
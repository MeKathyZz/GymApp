package com.gymapp.demo.controller;

import com.gymapp.demo.model.Exercise;
import com.gymapp.demo.repository.ExerciseRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")

public class ExerciseController {

    private final ExerciseRepository repository;
    public ExerciseController(ExerciseRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Exercise> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Exercise create(@Valid @RequestBody Exercise exercise) { // Adicione o @Valid aqui
        return repository.save(exercise);
    }
}

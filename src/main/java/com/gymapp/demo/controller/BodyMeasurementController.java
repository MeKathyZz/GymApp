package com.gymapp.demo.controller;

import com.gymapp.demo.model.BodyMeasurement;
import com.gymapp.demo.repository.BodyMeasurementRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class BodyMeasurementController {

    private final BodyMeasurementRepository repository;

    public BodyMeasurementController(BodyMeasurementRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<BodyMeasurement> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public BodyMeasurement create(@RequestBody BodyMeasurement measurement) {
        return repository.save(measurement);
    }
}
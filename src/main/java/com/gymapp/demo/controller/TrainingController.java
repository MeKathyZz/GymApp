package com.gymapp.demo.controller;

import com.gymapp.demo.dto.CoreResponseDTO;
import com.gymapp.demo.dto.TrainingFlowDTO;
import com.gymapp.demo.dto.TrainingResponseDTO;
import com.gymapp.demo.service.TrainingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    private final TrainingService service;

    public TrainingController(TrainingService service) {
        this.service = service;
    }

    @GetMapping("/{id}/flow")
    public ResponseEntity<TrainingFlowDTO> getFlow(@PathVariable Long id) {
        return ResponseEntity.ok(service.getTrainingFlow(id));
    }

    @GetMapping("/today")
    public ResponseEntity<TrainingResponseDTO> getToday(@RequestParam(defaultValue = "FULL") String mode) {
        return ResponseEntity.ok(service.getTodayTraining(mode));
    }

    @PostMapping("/{id}/complete-mobility")
    public ResponseEntity<Void> completeMobility(@PathVariable Long id) {
        service.completeMobility(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/core/today")
    public ResponseEntity<CoreResponseDTO> getCoreToday() {
        return ResponseEntity.ok(service.getDailyCore());
    }

}
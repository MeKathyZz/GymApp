package com.gymapp.demo.controller;

import com.gymapp.demo.dto.TrainingRecommendationDTO;
import com.gymapp.demo.model.UserStatus;
import com.gymapp.demo.service.UserStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status")
public class UserStatusController {

    private final UserStatusService service;

    public UserStatusController(UserStatusService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserStatus> recordStatus(@RequestBody UserStatus status) {
        return ResponseEntity.ok(service.saveStatus(status));
    }

    @GetMapping("/last")
    public ResponseEntity<UserStatus> getLast() {
        return service.getLastStatus()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/recommendation")
    public ResponseEntity<TrainingRecommendationDTO> getRecommendation() {
        return ResponseEntity.ok(service.getSmartRecommendation());
    }

    @GetMapping("/weekly-report")
    public ResponseEntity<String> getWeeklyReport() {
        return ResponseEntity.ok(service.getLastWeeklyVeredict());
    }

}
package com.gymapp.demo.controller;

import com.gymapp.demo.model.InjuryLog;
import com.gymapp.demo.repository.InjuryLogRepository;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/injuries")
public class InjuryLogController {

    private final InjuryLogRepository repository;

    public InjuryLogController(InjuryLogRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public InjuryLog reportPain(@RequestBody InjuryLog injury) {
        return repository.save(injury);
    }

    @GetMapping("/active")
    public List<InjuryLog> getRecentInjuries() {
        return repository.findTop3ByOrderByDateDesc();
    }
}
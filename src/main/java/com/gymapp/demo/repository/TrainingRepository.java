package com.gymapp.demo.repository;

import com.gymapp.demo.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    Optional<Training> findByScheduledDaysContains(java.time.DayOfWeek day);
}
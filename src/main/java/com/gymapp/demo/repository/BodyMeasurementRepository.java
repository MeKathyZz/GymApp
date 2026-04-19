package com.gymapp.demo.repository;

import com.gymapp.demo.model.BodyMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyMeasurementRepository extends JpaRepository<BodyMeasurement, Long> {
}
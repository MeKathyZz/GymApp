package com.gymapp.demo.repository;

import com.gymapp.demo.model.InjuryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InjuryLogRepository extends JpaRepository<InjuryLog, Long> {
    List<InjuryLog> findTop3ByOrderByDateDesc();
}
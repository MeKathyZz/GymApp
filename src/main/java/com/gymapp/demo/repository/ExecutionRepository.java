package com.gymapp.demo.repository;

import com.gymapp.demo.dto.PersonalRecordDTO;
import com.gymapp.demo.model.Execution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ExecutionRepository extends JpaRepository<Execution, Long> {
    List<Execution> findAllByTimestampAfter(LocalDateTime startOfWeek);
    List<Execution> findAllByTimestampBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new com.gymapp.demo.dto.PersonalRecordDTO(ee.exercise.name, MAX(ee.weightUsed), MAX(ee.repsDone)) " +
            "FROM ExerciseExecution ee " +
            "GROUP BY ee.exercise.name")
    List<PersonalRecordDTO> findPersonalRecords();
}
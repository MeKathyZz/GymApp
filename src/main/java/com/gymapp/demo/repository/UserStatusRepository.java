package com.gymapp.demo.repository;

import com.gymapp.demo.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
    Optional<UserStatus> findFirstByOrderByTimestampDesc();

    List<UserStatus> findAllByTimestampAfter(java.time.LocalDateTime dateTime);
}
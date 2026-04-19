package com.gymapp.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private CheckMoment moment;

    private Integer painLevel;
    private String soreMuscles;
    private String observations;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}
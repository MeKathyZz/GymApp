package com.gymapp.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class InjuryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bodyPart;
    private Integer painLevel;
    private String description;
    private LocalDate date;

    private Boolean isCritical;

    @PrePersist
    protected void onCreate() {
        if (this.date == null) this.date = LocalDate.now();
    }
}
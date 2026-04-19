package com.gymapp.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BodyMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Double weight;
    private Double height;

    private Double chest;
    private Double waist;
    private Double rightBicep;
    private Double leftBicep;
    private Double rightForearm;
    private Double leftForearm;
    private Double glutes;
    private Double rightThigh;
    private Double leftThigh;
    private Double rightCalf;
    private Double leftCalf;


    @PrePersist
    protected void onCreate() {
        if (this.date == null) {
            this.date = LocalDate.now();
        }
    }
}
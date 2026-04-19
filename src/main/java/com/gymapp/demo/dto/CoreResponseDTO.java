package com.gymapp.demo.dto;

import com.gymapp.demo.model.Exercise;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

    @Getter
    @AllArgsConstructor
    public class CoreResponseDTO {
        private String intensity; // HOT ou LIGHT
        private List<Exercise> exercises;
        private String message;
    }
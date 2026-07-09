package com.interviewplatform.backend.bot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenerateRequest {

    @NotBlank(message = "role is required")
    private String role;

    @NotBlank(message = "experience is required")
    private String experience;

    @NotBlank(message = "difficulty is required")
    private String difficulty;

    @Min(value = 1, message = "questionCount must be at least 1")
    private int questionCount;
}

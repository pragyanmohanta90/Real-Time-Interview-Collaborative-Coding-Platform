package com.interviewplatform.backend.bot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class EvaluateRequest {

    @NotBlank(message = "role is required")
    private String role;

    @NotBlank(message = "experience is required")
    private String experience;

    @NotBlank(message = "difficulty is required")
    private String difficulty;

    @NotEmpty(message = "questions are required")
    private List<String> questions;

    private Map<String, String> answers;
}

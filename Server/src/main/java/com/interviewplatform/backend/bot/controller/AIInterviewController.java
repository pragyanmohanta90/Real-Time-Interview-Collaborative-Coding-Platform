package com.interviewplatform.backend.bot.controller;

import com.interviewplatform.backend.bot.dto.EvaluateRequest;
import com.interviewplatform.backend.bot.dto.EvaluationResponse;
import com.interviewplatform.backend.bot.dto.GenerateRequest;
import com.interviewplatform.backend.bot.service.AIInterviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interview")
@RequiredArgsConstructor
public class AIInterviewController {

    private final AIInterviewService interviewService;

    /**
     * Calls Gemini ONCE to generate the full question list for the interview.
     */
    @PostMapping("/generate")
    public ResponseEntity<Map<String, List<String>>> generate(@Valid @RequestBody GenerateRequest request) {
        List<String> questions = interviewService.generateQuestions(request);
        return ResponseEntity.ok(Map.of("questions", questions));
    }

    /**
     * Calls Gemini ONCE to evaluate the full set of answers, then saves the
     * complete interview (questions + answers + evaluation) to MongoDB.
     */
    @PostMapping("/evaluate")
    public ResponseEntity<EvaluationResponse> evaluate(@Valid @RequestBody EvaluateRequest request) {
        return ResponseEntity.ok(interviewService.evaluateInterview(request));
    }
}

package com.interviewplatform.backend.candidate.controller;

import com.interviewplatform.backend.candidate.dto.practice.PracticeQuestionResponse;
import com.interviewplatform.backend.candidate.service.PracticeQuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.interviewplatform.backend.candidate.dto.practice.SubmissionResponse;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/candidate/practice")
public class PracticeQuestionController {

    // Practice Question Service
    private final PracticeQuestionService practiceQuestionService;

    // Constructor
    public PracticeQuestionController(PracticeQuestionService practiceQuestionService) {
        this.practiceQuestionService = practiceQuestionService;
    }

    // Get Practice Questions API
    @GetMapping("/questions")
    public List<PracticeQuestionResponse> getPracticeQuestions(
            @RequestParam(required = false) String difficulty
    ) {

        // Return Questions
        return practiceQuestionService.getPracticeQuestions(difficulty);
    }

    // Practice Progress API
    @GetMapping("/progress")
    public SubmissionResponse getPracticeProgress() {

        // Return Progress
        return practiceQuestionService.getPracticeProgress();
    }
}
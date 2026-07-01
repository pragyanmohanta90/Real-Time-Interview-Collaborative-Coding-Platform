package com.interviewplatform.backend.candidate.controller;

import com.interviewplatform.backend.candidate.dto.practice.PracticeQuestionDetailResponse;
import com.interviewplatform.backend.candidate.dto.practice.PracticeQuestionResponse;
import com.interviewplatform.backend.candidate.dto.practice.SubmitCodeRequest;
import com.interviewplatform.backend.candidate.dto.practice.SubmissionResponse;
import com.interviewplatform.backend.candidate.service.PracticeQuestionService;
import com.interviewplatform.backend.candidate.service.QuestionSubmissionService;
import com.interviewplatform.backend.model.QuestionSubmission;
import com.interviewplatform.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate/practice")
@CrossOrigin(origins = "http://localhost:5173")
public class PracticeQuestionController {

    // Services
    private final PracticeQuestionService practiceQuestionService;
    private final QuestionSubmissionService questionSubmissionService;
    private final UserService userService;

    // Constructor
    public PracticeQuestionController(
            PracticeQuestionService practiceQuestionService,
            QuestionSubmissionService questionSubmissionService,
            UserService userService
    ) {
        this.practiceQuestionService = practiceQuestionService;
        this.questionSubmissionService = questionSubmissionService;
        this.userService = userService;
    }

    // Get All Questions
    @GetMapping("/questions")
    public List<PracticeQuestionResponse> getPracticeQuestions(
            @RequestParam(required = false) String difficulty
    ) {
        return practiceQuestionService.getPracticeQuestions(difficulty);
    }

    // Get Question Details
    @GetMapping("/questions/{id}")
    public PracticeQuestionDetailResponse getQuestionById(
            @PathVariable String id
    ) {
        return practiceQuestionService.getQuestionById(id);
    }

    // Get Progress
    @GetMapping("/progress")
    public SubmissionResponse getPracticeProgress() {
        return practiceQuestionService.getPracticeProgress();
    }

    // Submit Code
    @PostMapping("/questions/{id}/submit")
    public QuestionSubmission submitCode(
            @PathVariable String id,
            @RequestBody SubmitCodeRequest request
    ) {

        return questionSubmissionService.saveSubmission(
                userService.getLoggedInUser().getId(),
                id,
                request.getLanguage(),
                request.getCode(),
                request.getPassedTestCases(),
                request.getTotalTestCases()
        );
    }
}
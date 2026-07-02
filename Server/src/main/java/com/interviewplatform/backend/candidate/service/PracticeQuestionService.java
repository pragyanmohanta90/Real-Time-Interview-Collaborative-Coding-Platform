package com.interviewplatform.backend.candidate.service;

import com.interviewplatform.backend.candidate.dto.practice.PracticeQuestionDetailResponse;
import com.interviewplatform.backend.candidate.dto.practice.PracticeQuestionResponse;
import com.interviewplatform.backend.candidate.dto.practice.SubmissionResponse;
import com.interviewplatform.backend.model.Difficulty;
import com.interviewplatform.backend.model.Example;
import com.interviewplatform.backend.model.Question;
import com.interviewplatform.backend.model.User;
import com.interviewplatform.backend.repository.QuestionRepository;
import com.interviewplatform.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PracticeQuestionService {

    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final QuestionSubmissionService questionSubmissionService;

    public PracticeQuestionService(
            QuestionRepository questionRepository,
            UserService userService,
            QuestionSubmissionService questionSubmissionService
    ) {
        this.questionRepository = questionRepository;
        this.userService = userService;
        this.questionSubmissionService = questionSubmissionService;
    }

    // Get Practice Questions
    public List<PracticeQuestionResponse> getPracticeQuestions(String difficulty) {

        User user = userService.getLoggedInUser();

        List<Question> questions;

        if (difficulty == null || difficulty.isBlank()) {
            questions = questionRepository.findAll();
        } else {
            questions = questionRepository.findByDifficulty(
                    Difficulty.valueOf(difficulty.toUpperCase())
            );
        }

        List<PracticeQuestionResponse> response = new ArrayList<>();

        for (Question question : questions) {

            boolean solved = questionSubmissionService.isSolved(
                    user.getId(),
                    question.getId()
            );

            response.add(
                    new PracticeQuestionResponse(
                            question.getId(),
                            question.getTitle(),
                            question.getCategory(),
                            question.getDifficulty().name(),
                            solved,
                            question.getEstimatedTime() == null
                                    ? null
                                    : question.getEstimatedTime() + " min"
                    )
            );
        }

        return response;
    }

    // Get Practice Progress
    public SubmissionResponse getPracticeProgress() {

        User user = userService.getLoggedInUser();

        SubmissionResponse response = new SubmissionResponse();

        response.setCompleted(
                (int) questionSubmissionService.getSolvedCount(user.getId())
        );

        response.setTotal(
                (int) questionRepository.count()
        );

        return response;
    }

    // Get Question Details
    public PracticeQuestionDetailResponse getQuestionById(String id) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        PracticeQuestionDetailResponse response =
                new PracticeQuestionDetailResponse();

        response.setId(question.getId());
        response.setTitle(question.getTitle());
        response.setCategory(question.getCategory());
        response.setDifficulty(question.getDifficulty().name());
        response.setDescription(question.getDescription());

        List<String> exampleList = new ArrayList<>();

        if (question.getExamples() != null) {

            for (Example example : question.getExamples()) {

                StringBuilder builder = new StringBuilder();

                if (example.getInput() != null && !example.getInput().isBlank()) {
                    builder.append("Input: ").append(example.getInput());
                }

                if (example.getOutput() != null && !example.getOutput().isBlank()) {
                    builder.append("\nOutput: ").append(example.getOutput());
                }

                if (example.getExplanation() != null && !example.getExplanation().isBlank()) {
                    builder.append("\nExplanation: ").append(example.getExplanation());
                }

                exampleList.add(builder.toString());
            }
        }

        response.setExamples(exampleList);
        response.setConstraints(question.getConstraints());
        response.setTags(question.getTags());

        if (question.getStarterCode() != null) {
            response.setStarterCode(question.getStarterCode().getJava());
        }

        return response;
    }
}
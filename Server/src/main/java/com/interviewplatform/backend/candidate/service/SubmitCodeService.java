package com.interviewplatform.backend.candidate.service;

import com.interviewplatform.backend.candidate.codegen.JavaDriverGenerator;
import com.interviewplatform.backend.candidate.dto.codeeditor.SubmitCodeResponse;
import com.interviewplatform.backend.candidate.dto.practice.SubmitCodeRequest;
import com.interviewplatform.backend.integration.onlinecompiler.OnlineCompilerClient;
import com.interviewplatform.backend.integration.onlinecompiler.OnlineCompilerRequest;
import com.interviewplatform.backend.integration.onlinecompiler.OnlineCompilerResponse;
import com.interviewplatform.backend.model.Question;
import com.interviewplatform.backend.model.TestCase;
import com.interviewplatform.backend.model.User;
import com.interviewplatform.backend.repository.QuestionRepository;
import com.interviewplatform.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmitCodeService {

    // Repositories
    private final QuestionRepository questionRepository;

    // Services
    private final UserService userService;
    private final QuestionSubmissionService questionSubmissionService;
    private final JavaDriverGenerator javaDriverGenerator;

    // Compiler
    private final OnlineCompilerClient onlineCompilerClient;

    // Constructor
    public SubmitCodeService(
            QuestionRepository questionRepository,
            UserService userService,
            QuestionSubmissionService questionSubmissionService,
            JavaDriverGenerator javaDriverGenerator,
            OnlineCompilerClient onlineCompilerClient
    ) {
        this.questionRepository = questionRepository;
        this.userService = userService;
        this.questionSubmissionService = questionSubmissionService;
        this.javaDriverGenerator = javaDriverGenerator;
        this.onlineCompilerClient = onlineCompilerClient;
    }

    public SubmitCodeResponse submitCode(
            String questionId,
            SubmitCodeRequest request
    ) {

        // Fetch User
        User user = userService.getLoggedInUser();

        // Fetch Question
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Test Cases
        List<TestCase> testCases = question.getTestCases();

        // Generate Driver
        String sourceCode = javaDriverGenerator.generate(
                request.getCode(),
                question.getExecutionMetadata(),
                testCases
        );

        // Compiler Request
        OnlineCompilerRequest compilerRequest = new OnlineCompilerRequest();

        compilerRequest.setCompiler(getCompiler(request.getLanguage()));
        compilerRequest.setCode(sourceCode);
        compilerRequest.setInput("");

        // Execute
        OnlineCompilerResponse compilerResponse =
                onlineCompilerClient.execute(compilerRequest);

        SubmitCodeResponse response = new SubmitCodeResponse();

        response.setLanguage(request.getLanguage());
        response.setRuntime(compilerResponse.getExecutionTime());
        response.setMemory(compilerResponse.getMemory());
        response.setSubmittedAt(LocalDateTime.now());


        // Runtime Error
        if (compilerResponse.getError() != null &&
                !compilerResponse.getError().isBlank()) {

            response.setStatus("Runtime Error");
            response.setPassedTestCases(0);
            response.setTotalTestCases(testCases.size());

            questionSubmissionService.saveSubmission(
                    user.getId(),
                    questionId,
                    request.getLanguage(),
                    request.getCode(),
                    0,
                    testCases.size()
            );

            return response;
        }

// Judge Output
        String output = compilerResponse.getOutput() == null
                ? ""
                : compilerResponse.getOutput().trim();

        String[] actualOutputs = output.split("\\R");

        int passed = 0;

        for (int i = 0; i < testCases.size() && i < actualOutputs.length; i++) {

            String expected = testCases.get(i)
                    .getExpectedOutput()
                    .replaceAll("\\s+", "")
                    .trim();

            String actual = actualOutputs[i]
                    .replaceAll("\\s+", "")
                    .trim();

            if (expected.equals(actual)) {
                passed++;
            }
        }

// Build Response
        response.setPassedTestCases(passed);
        response.setTotalTestCases(testCases.size());

        if (passed == testCases.size()) {
            response.setStatus("Accepted");
        } else {
            response.setStatus("Wrong Answer");
        }

// Save Submission
        questionSubmissionService.saveSubmission(
                user.getId(),
                questionId,
                request.getLanguage(),
                request.getCode(),
                passed,
                testCases.size()
        );

        return response;
    }

    // Compiler
    private String getCompiler(String language) {

        switch (language.toLowerCase()) {

            case "java":
                return "openjdk-25";

            case "python":
            case "python3":
                return "python-3.14";

            case "cpp":
            case "c++":
                return "g++-15";

            default:
                throw new IllegalArgumentException(
                        "Unsupported language: " + language
                );
        }
    }

}
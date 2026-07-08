package com.interviewplatform.backend.candidate.service;

import com.interviewplatform.backend.candidate.dto.codeeditor.RunCodeRequest;
import com.interviewplatform.backend.candidate.dto.codeeditor.RunCodeResponse;
import com.interviewplatform.backend.integration.onlinecompiler.OnlineCompilerClient;
import com.interviewplatform.backend.integration.onlinecompiler.OnlineCompilerRequest;
import com.interviewplatform.backend.integration.onlinecompiler.OnlineCompilerResponse;
import com.interviewplatform.backend.model.Question;
import com.interviewplatform.backend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class RunCodeService {

    private final OnlineCompilerClient onlineCompilerClient;
    private final QuestionRepository questionRepository;

    public RunCodeService(
            OnlineCompilerClient onlineCompilerClient,
            QuestionRepository questionRepository
    ) {
        this.onlineCompilerClient = onlineCompilerClient;
        this.questionRepository = questionRepository;
    }

    public RunCodeResponse executeCode(RunCodeRequest request) {

        // Validate Request
        if (request.getQuestionId() == null || request.getQuestionId().isBlank()) {
            throw new IllegalArgumentException("Question ID is required.");
        }

        if (request.getLanguage() == null || request.getLanguage().isBlank()) {
            throw new IllegalArgumentException("Programming language is required.");
        }

        if (request.getCode() == null || request.getCode().isBlank()) {
            throw new IllegalArgumentException("Source code is required.");
        }

        // Fetch Question
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found."));

        // Build Compiler Request
        OnlineCompilerRequest compilerRequest = new OnlineCompilerRequest();
        compilerRequest.setCompiler(getCompiler(request.getLanguage()));
        compilerRequest.setCode(request.getCode());
        compilerRequest.setInput(
                request.getInput() == null ? "" : request.getInput()
        );

        // Execute Code
        OnlineCompilerResponse compilerResponse =
                onlineCompilerClient.execute(compilerRequest);

        // Build Response
        RunCodeResponse response = new RunCodeResponse();

        response.setStatus(compilerResponse.getStatus());
        response.setOutput(compilerResponse.getOutput());
        response.setError(compilerResponse.getError());
        response.setRuntime(compilerResponse.getExecutionTime());
        response.setMemory(compilerResponse.getMemory());

        // Example test case count (Run does not judge)
        response.setPassedTestCases(0);
        response.setTotalTestCases(
                question.getTestCases() == null
                        ? 0
                        : question.getTestCases().size()
        );

        return response;
    }

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
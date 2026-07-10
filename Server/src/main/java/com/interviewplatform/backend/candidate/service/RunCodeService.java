package com.interviewplatform.backend.candidate.service;

import com.interviewplatform.backend.candidate.dto.codeeditor.RunCodeRequest;
import com.interviewplatform.backend.candidate.dto.codeeditor.RunCodeResponse;
import com.interviewplatform.backend.integration.onlinecompiler.OnlineCompilerClient;
import com.interviewplatform.backend.integration.onlinecompiler.OnlineCompilerRequest;
import com.interviewplatform.backend.integration.onlinecompiler.OnlineCompilerResponse;
import com.interviewplatform.backend.model.Question;
import com.interviewplatform.backend.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import com.interviewplatform.backend.candidate.codegen.JavaDriverGenerator;
import com.interviewplatform.backend.candidate.codegen.PythonDriverGenerator;
import com.interviewplatform.backend.model.TestCase;

import java.util.List;

@Service
public class RunCodeService {

    private final OnlineCompilerClient onlineCompilerClient;
    private final QuestionRepository questionRepository;
    private final JavaDriverGenerator javaDriverGenerator;
    private final PythonDriverGenerator pythonDriverGenerator;

    public RunCodeService(
            OnlineCompilerClient onlineCompilerClient,
            QuestionRepository questionRepository,
            JavaDriverGenerator javaDriverGenerator,
            PythonDriverGenerator pythonDriverGenerator
    ) {
        this.onlineCompilerClient = onlineCompilerClient;
        this.questionRepository = questionRepository;
        this.javaDriverGenerator = javaDriverGenerator;
        this.pythonDriverGenerator = pythonDriverGenerator;
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

        // Test Cases
        List<TestCase> testCases = question.getTestCases();

        // Build Compiler Request
        OnlineCompilerRequest compilerRequest = new OnlineCompilerRequest();
        compilerRequest.setCompiler(getCompiler(request.getLanguage()));
        String sourceCode;

        switch (request.getLanguage().toLowerCase()) {

            case "java":
                sourceCode = javaDriverGenerator.generate(
                        request.getCode(),
                        question.getExecutionMetadata(),
                        testCases
                );
                break;

            case "python":
            case "python3":
                sourceCode = pythonDriverGenerator.generate(
                        request.getCode(),
                        question.getExecutionMetadata(),
                        testCases
                );
                break;

            case "cpp":
            case "c++":
                sourceCode = request.getCode(); // Temporary
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported language: " + request.getLanguage()
                );
        }

        compilerRequest.setCode(sourceCode);
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
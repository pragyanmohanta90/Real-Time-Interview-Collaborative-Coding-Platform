package com.interviewplatform.backend.candidate.dto.codeeditor;

public class RunCodeRequest {

    // Question ID
    private String questionId;

    // Programming Language
    private String language;

    // User Code
    private String code;

    // Custom Input (Optional)
    private String input;

    // Default Constructor
    public RunCodeRequest() {
    }

    // Getter
    public String getQuestionId() {
        return questionId;
    }

    // Setter
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    // Getter
    public String getLanguage() {
        return language;
    }

    // Setter
    public void setLanguage(String language) {
        this.language = language;
    }

    // Getter
    public String getCode() {
        return code;
    }

    // Setter
    public void setCode(String code) {
        this.code = code;
    }

    // Getter
    public String getInput() {
        return input;
    }

    // Setter
    public void setInput(String input) {
        this.input = input;
    }
}
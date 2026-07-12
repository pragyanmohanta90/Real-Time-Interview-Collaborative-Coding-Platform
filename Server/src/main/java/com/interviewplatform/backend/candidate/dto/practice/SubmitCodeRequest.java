package com.interviewplatform.backend.candidate.dto.practice;

public class SubmitCodeRequest {

    // Programming Language
    private String language;

    // User Source Code
    private String code;

    // Coding Time (Seconds)
    private long codingTimeSeconds;

    // Default Constructor
    public SubmitCodeRequest() {
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
    public long getCodingTimeSeconds() {
        return codingTimeSeconds;
    }

    // Setter
    public void setCodingTimeSeconds(long codingTimeSeconds) {
        this.codingTimeSeconds = codingTimeSeconds;
    }
}
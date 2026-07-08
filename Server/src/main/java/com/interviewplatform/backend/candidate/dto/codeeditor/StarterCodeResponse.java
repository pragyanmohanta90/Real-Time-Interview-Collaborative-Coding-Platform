package com.interviewplatform.backend.candidate.dto.codeeditor;

public class StarterCodeResponse {

    // Language
    private String language;

    // Starter Code
    private String code;

    // Default Constructor
    public StarterCodeResponse() {
    }

    // Parameterized Constructor
    public StarterCodeResponse(String language, String code) {
        this.language = language;
        this.code = code;
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
}
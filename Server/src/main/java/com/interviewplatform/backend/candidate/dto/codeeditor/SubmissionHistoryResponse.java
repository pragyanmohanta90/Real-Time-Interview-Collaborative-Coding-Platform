package com.interviewplatform.backend.candidate.dto.codeeditor;

import java.time.LocalDateTime;

public class SubmissionHistoryResponse {

    // Submission ID
    private String id;

    // Language
    private String language;

    // Status
    private String status;

    // Passed Test Cases
    private int passedTestCases;

    // Total Test Cases
    private int totalTestCases;

    // Submission Time
    private LocalDateTime submittedAt;

    // Default Constructor
    public SubmissionHistoryResponse() {
    }

    // Getter
    public String getId() {
        return id;
    }

    // Setter
    public void setId(String id) {
        this.id = id;
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
    public String getStatus() {
        return status;
    }

    // Setter
    public void setStatus(String status) {
        this.status = status;
    }

    // Getter
    public int getPassedTestCases() {
        return passedTestCases;
    }

    // Setter
    public void setPassedTestCases(int passedTestCases) {
        this.passedTestCases = passedTestCases;
    }

    // Getter
    public int getTotalTestCases() {
        return totalTestCases;
    }

    // Setter
    public void setTotalTestCases(int totalTestCases) {
        this.totalTestCases = totalTestCases;
    }

    // Getter
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    // Setter
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
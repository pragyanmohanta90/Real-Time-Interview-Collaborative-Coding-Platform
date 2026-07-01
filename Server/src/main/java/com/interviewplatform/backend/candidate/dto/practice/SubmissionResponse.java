package com.interviewplatform.backend.candidate.dto.practice;

// Class
public class SubmissionResponse {

    // Fields
    private int completed;
    private int total;

    // Default Constructor
    public SubmissionResponse() {
    }

    // Parameterized Constructor
    public SubmissionResponse(int completed, int total) {
        this.completed = completed;
        this.total = total;
    }

    // Getter
    public int getCompleted() {
        return completed;
    }

    // Setter
    public void setCompleted(int completed) {
        this.completed = completed;
    }

    // Getter
    public int getTotal() {
        return total;
    }

    // Setter
    public void setTotal(int total) {
        this.total = total;
    }
}
package com.interviewplatform.backend.candidate.dto.dashboard;

// Class
public class SkillBreakdownResponse {

    // Fields
    private int confidence;
    private int technical;
    private int readiness;
    private int problemSolving;
    private int communication;

    // Default Constructor
    public SkillBreakdownResponse() {
    }

    // Parameterized Constructor
    public SkillBreakdownResponse(int confidence, int technical, int readiness,
                                  int problemSolving, int communication) {
        this.confidence = confidence;
        this.technical = technical;
        this.readiness = readiness;
        this.problemSolving = problemSolving;
        this.communication = communication;
    }

    // Getter
    public int getConfidence() {
        return confidence;
    }

    // Setter
    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    // Getter
    public int getTechnical() {
        return technical;
    }

    // Setter
    public void setTechnical(int technical) {
        this.technical = technical;
    }

    // Getter
    public int getReadiness() {
        return readiness;
    }

    // Setter
    public void setReadiness(int readiness) {
        this.readiness = readiness;
    }

    // Getter
    public int getProblemSolving() {
        return problemSolving;
    }

    // Setter
    public void setProblemSolving(int problemSolving) {
        this.problemSolving = problemSolving;
    }

    // Getter
    public int getCommunication() {
        return communication;
    }

    // Setter
    public void setCommunication(int communication) {
        this.communication = communication;
    }
}
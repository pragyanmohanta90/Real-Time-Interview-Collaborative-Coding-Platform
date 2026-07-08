package com.interviewplatform.backend.candidate.dto.codeeditor;

public class RunCodeResponse {

    // Execution Status
    private String status;

    // Console Output
    private String output;

    // Error Message
    private String error;

    // Runtime
    private String runtime;

    // Memory Used
    private String memory;

    // Passed Test Cases
    private int passedTestCases;

    // Total Test Cases
    private int totalTestCases;

    // Default Constructor
    public RunCodeResponse() {
    }

    // Getters & Setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public int getPassedTestCases() {
        return passedTestCases;
    }

    public void setPassedTestCases(int passedTestCases) {
        this.passedTestCases = passedTestCases;
    }

    public int getTotalTestCases() {
        return totalTestCases;
    }

    public void setTotalTestCases(int totalTestCases) {
        this.totalTestCases = totalTestCases;
    }
}
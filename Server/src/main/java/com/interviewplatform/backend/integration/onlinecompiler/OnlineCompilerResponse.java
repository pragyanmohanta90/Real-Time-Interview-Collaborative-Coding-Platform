package com.interviewplatform.backend.integration.onlinecompiler;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OnlineCompilerResponse {

    private String output;

    private String error;

    private String status;

    @JsonProperty("exit_code")
    private Integer exitCode;

    @JsonProperty("time")
    private String executionTime;

    private String memory;

    // Default Constructor
    public OnlineCompilerResponse() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getExitCode() {
        return exitCode;
    }

    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }
}
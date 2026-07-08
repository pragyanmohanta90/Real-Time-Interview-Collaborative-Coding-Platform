package com.interviewplatform.backend.candidate.dto.codeeditor;

public class TestCaseResponse {

    // Input
    private String input;

    // Expected Output
    private String expectedOutput;

    // Hidden Test Case
    private boolean hidden;

    // Default Constructor
    public TestCaseResponse() {
    }

    // Parameterized Constructor
    public TestCaseResponse(
            String input,
            String expectedOutput,
            boolean hidden
    ) {
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.hidden = hidden;
    }

    // Getter
    public String getInput() {
        return input;
    }

    // Setter
    public void setInput(String input) {
        this.input = input;
    }

    // Getter
    public String getExpectedOutput() {
        return expectedOutput;
    }

    // Setter
    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    // Getter
    public boolean isHidden() {
        return hidden;
    }

    // Setter
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
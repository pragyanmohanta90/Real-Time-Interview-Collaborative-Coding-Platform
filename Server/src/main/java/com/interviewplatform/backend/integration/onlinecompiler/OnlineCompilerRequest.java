package com.interviewplatform.backend.integration.onlinecompiler;

public class OnlineCompilerRequest {

    // Compiler Name
    private String compiler;

    // Source Code
    private String code;

    // Standard Input
    private String input;

    // Default Constructor
    public OnlineCompilerRequest() {
    }

    // Getter
    public String getCompiler() {
        return compiler;
    }

    // Setter
    public void setCompiler(String compiler) {
        this.compiler = compiler;
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
package com.interviewplatform.backend.importer.dto;

public class CodeSnippetsJson {

    private String java;
    private String cpp;
    private String javascript;
    private String python3;

    public CodeSnippetsJson() {
    }

    public String getJava() {
        return java;
    }

    public void setJava(String java) {
        this.java = java;
    }

    public String getCpp() {
        return cpp;
    }

    public void setCpp(String cpp) {
        this.cpp = cpp;
    }

    public String getJavascript() {
        return javascript;
    }

    public void setJavascript(String javascript) {
        this.javascript = javascript;
    }

    public String getPython3() {
        return python3;
    }

    public void setPython3(String python3) {
        this.python3 = python3;
    }
}
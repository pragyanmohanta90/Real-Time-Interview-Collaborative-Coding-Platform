package com.interviewplatform.backend.model;

public class StarterCode {

    private String java;

    private String python;

    private String cpp;

    private String javascript;

    public StarterCode() {
    }

    public StarterCode(String java, String python, String cpp, String javascript) {
        this.java = java;
        this.python = python;
        this.cpp = cpp;
        this.javascript = javascript;
    }

    public String getJava() {
        return java;
    }

    public void setJava(String java) {
        this.java = java;
    }

    public String getPython() {
        return python;
    }

    public void setPython(String python) {
        this.python = python;
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
}
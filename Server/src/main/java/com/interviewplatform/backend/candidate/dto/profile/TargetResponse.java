package com.interviewplatform.backend.candidate.dto.profile;

public class TargetResponse {

    private String target;

    public TargetResponse() {
    }

    public TargetResponse(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
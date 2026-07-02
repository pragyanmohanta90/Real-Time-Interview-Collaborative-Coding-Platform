package com.interviewplatform.backend.candidate.dto.dashboard;

public class TargetResponse {

    // Fields
    private String id;
    private String target;
    private boolean deleted;

    // Default Constructor
    public TargetResponse() {
    }

    // Parameterized Constructor
    public TargetResponse(String id, String target, boolean deleted) {
        this.id = id;
        this.target = target;
        this.deleted = deleted;
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
    public String getTarget() {
        return target;
    }

    // Setter
    public void setTarget(String target) {
        this.target = target;
    }

    // Getter
    public boolean isDeleted() {
        return deleted;
    }

    // Setter
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
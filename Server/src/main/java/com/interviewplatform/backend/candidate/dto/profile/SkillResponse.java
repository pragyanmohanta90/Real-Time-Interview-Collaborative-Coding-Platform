package com.interviewplatform.backend.candidate.dto.profile;

public class SkillResponse {

    private String id;

    private String skill;

    private boolean deleted;

    public SkillResponse() {
    }

    public SkillResponse(String id, String skill) {
        this.id = id;
        this.skill = skill;
    }

    public SkillResponse(String id, String skill, boolean deleted) {
        this.id = id;
        this.skill = skill;
        this.deleted = deleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
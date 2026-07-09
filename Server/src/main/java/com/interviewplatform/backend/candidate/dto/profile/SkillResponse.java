package com.interviewplatform.backend.candidate.dto.profile;

public class SkillResponse {

    private String skill;

    public SkillResponse() {
    }

    public SkillResponse(String skill) {
        this.skill = skill;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
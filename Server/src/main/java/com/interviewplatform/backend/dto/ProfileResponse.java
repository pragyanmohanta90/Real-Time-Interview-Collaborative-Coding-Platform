package com.interviewplatform.backend.dto;

import com.interviewplatform.backend.model.Experience;
import java.util.List;

public class ProfileResponse {

    private String id;
    private String name;
    private String email;
    private String title;
    private String location;
    private String avatar;
    private String about;
    private Integer readinessScore;

    private List<String> skills;
    private List<String> targets;
    private List<Experience> experience;

    public ProfileResponse() {
    }

    public ProfileResponse(
            String id,
            String name,
            String email,
            String title,
            String location,
            String avatar,
            String about,
            Integer readinessScore,
            List<String> skills,
            List<String> targets,
            List<Experience> experience
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.title = title;
        this.location = location;
        this.avatar = avatar;
        this.about = about;
        this.readinessScore = readinessScore;
        this.skills = skills;
        this.targets = targets;
        this.experience = experience;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getAbout() {
        return about;
    }

    public Integer getReadinessScore() {
        return readinessScore;
    }

    public List<String> getSkills() {
        return skills;
    }

    public List<String> getTargets() {
        return targets;
    }

    public List<Experience> getExperience() {
        return experience;
    }
}
package com.interviewplatform.backend.candidate.dto.practice;

import java.util.List;

// Class for questions to code editor
public class PracticeQuestionDetailResponse {

    // Fields
    private String id;
    private String title;
    private String category;
    private String difficulty;
    private String description;
    private List<String> examples;
    private List<String> constraints;
    private List<String> tags;
    private String starterCode;

    // Default Constructor
    public PracticeQuestionDetailResponse() {
    }

    // Parameterized Constructor
    public PracticeQuestionDetailResponse(
            String id,
            String title,
            String category,
            String difficulty,
            String description,
            List<String> examples,
            List<String> constraints,
            List<String> tags,
            String starterCode
    ) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.difficulty = difficulty;
        this.description = description;
        this.examples = examples;
        this.constraints = constraints;
        this.tags = tags;
        this.starterCode = starterCode;
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
    public String getTitle() {
        return title;
    }

    // Setter
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter
    public String getCategory() {
        return category;
    }

    // Setter
    public void setCategory(String category) {
        this.category = category;
    }

    // Getter
    public String getDifficulty() {
        return difficulty;
    }

    // Setter
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    // Getter
    public String getDescription() {
        return description;
    }

    // Setter
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter
    public List<String> getExamples() {
        return examples;
    }

    // Setter
    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    // Getter
    public List<String> getConstraints() {
        return constraints;
    }

    // Setter
    public void setConstraints(List<String> constraints) {
        this.constraints = constraints;
    }

    // Getter
    public List<String> getTags() {
        return tags;
    }

    // Setter
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    // Getter
    public String getStarterCode() {
        return starterCode;
    }

    // Setter
    public void setStarterCode(String starterCode) {
        this.starterCode = starterCode;
    }
}
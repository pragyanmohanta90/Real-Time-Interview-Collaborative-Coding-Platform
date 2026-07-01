package com.interviewplatform.backend.candidate.dto.practice;

// Class
public class PracticeQuestionResponse {

    // Fields
    private String id;
    private String title;
    private String category;
    private String difficulty;
    private boolean completed;

    // Default Constructor
    public PracticeQuestionResponse() {
    }

    // Parameterized Constructor
    public PracticeQuestionResponse(String id, String title,
                                    String category, String difficulty,
                                    boolean completed) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.difficulty = difficulty;
        this.completed = completed;
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
    public boolean isCompleted() {
        return completed;
    }

    // Setter
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
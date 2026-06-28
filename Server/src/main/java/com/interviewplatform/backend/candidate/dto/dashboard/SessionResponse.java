package com.interviewplatform.backend.candidate.dto.dashboard;


public class SessionResponse {

    // Fields
    private String title;
    private String date;
    private String duration;
    private Integer score;
    private String status;

    // Default Constructor
    public SessionResponse() {
    }

    // Parameterized Constructor
    public SessionResponse(String title, String date, String duration,
                           Integer score, String status) {
        this.title = title;
        this.date = date;
        this.duration = duration;
        this.score = score;
        this.status = status;
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
    public String getDate() {
        return date;
    }

    // Setter
    public void setDate(String date) {
        this.date = date;
    }

    // Getter
    public String getDuration() {
        return duration;
    }

    // Setter
    public void setDuration(String duration) {
        this.duration = duration;
    }

    // Getter
    public Integer getScore() {
        return score;
    }

    // Setter
    public void setScore(Integer score) {
        this.score = score;
    }

    // Getter
    public String getStatus() {
        return status;
    }

    // Setter
    public void setStatus(String status) {
        this.status = status;
    }
}
package com.interviewplatform.backend.candidate.dto.dashboard;

public class ReadinessPoint {

    private String week;
    private int score;

    public ReadinessPoint() {
    }

    public ReadinessPoint(String week, int score) {
        this.week = week;
        this.score = score;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
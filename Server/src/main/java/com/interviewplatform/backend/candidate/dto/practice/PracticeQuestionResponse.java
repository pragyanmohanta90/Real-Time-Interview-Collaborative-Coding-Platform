package com.interviewplatform.backend.candidate.dto.practice;

public class PracticeQuestionResponse {

    private String id;

    private String title;

    // Frontend expects "topic"
    private String topic;

    private String difficulty;

    // Frontend expects "done"
    private boolean done;

    // Frontend expects "time"
    private String time;

    public PracticeQuestionResponse() {
    }

    public PracticeQuestionResponse(
            String id,
            String title,
            String topic,
            String difficulty,
            boolean done,
            String time
    ) {
        this.id = id;
        this.title = title;
        this.topic = topic;
        this.difficulty = difficulty;
        this.done = done;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
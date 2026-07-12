package com.interviewplatform.backend.candidate.dto.dashboard;

public class DashboardStatsResponse {

    private int questionsSolved;

    private long codingTimeSeconds;

    private int mockSessions;

    private int weeklyImprovement;

    public DashboardStatsResponse() {
    }

    public DashboardStatsResponse(
            int questionsSolved,
            long codingTimeSeconds,
            int mockSessions,
            int weeklyImprovement
    ) {
        this.questionsSolved = questionsSolved;
        this.codingTimeSeconds = codingTimeSeconds;
        this.mockSessions = mockSessions;
        this.weeklyImprovement = weeklyImprovement;
    }

    public int getQuestionsSolved() {
        return questionsSolved;
    }

    public void setQuestionsSolved(int questionsSolved) {
        this.questionsSolved = questionsSolved;
    }

    public long getCodingTimeSeconds() {
        return codingTimeSeconds;
    }

    public void setCodingTimeSeconds(long codingTimeSeconds) {
        this.codingTimeSeconds = codingTimeSeconds;
    }

    public int getMockSessions() {
        return mockSessions;
    }

    public void setMockSessions(int mockSessions) {
        this.mockSessions = mockSessions;
    }

    public int getWeeklyImprovement() {
        return weeklyImprovement;
    }

    public void setWeeklyImprovement(int weeklyImprovement) {
        this.weeklyImprovement = weeklyImprovement;
    }
}
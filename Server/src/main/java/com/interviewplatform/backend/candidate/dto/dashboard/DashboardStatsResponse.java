package com.interviewplatform.backend.candidate.dto.dashboard;

public class DashboardStatsResponse {

    private int questionsSolved;

    private int practiceHours;

    private int mockSessions;

    private int weeklyImprovement;

    public DashboardStatsResponse() {
    }

    public DashboardStatsResponse(
            int questionsSolved,
            int practiceHours,
            int mockSessions,
            int weeklyImprovement
    ) {
        this.questionsSolved = questionsSolved;
        this.practiceHours = practiceHours;
        this.mockSessions = mockSessions;
        this.weeklyImprovement = weeklyImprovement;
    }

    public int getQuestionsSolved() {
        return questionsSolved;
    }

    public void setQuestionsSolved(int questionsSolved) {
        this.questionsSolved = questionsSolved;
    }

    public int getPracticeHours() {
        return practiceHours;
    }

    public void setPracticeHours(int practiceHours) {
        this.practiceHours = practiceHours;
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
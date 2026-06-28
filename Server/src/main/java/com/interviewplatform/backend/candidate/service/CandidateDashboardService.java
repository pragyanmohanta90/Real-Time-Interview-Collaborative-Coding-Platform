package com.interviewplatform.backend.candidate.service;

import com.interviewplatform.backend.candidate.dto.dashboard.DashboardResponse;
import com.interviewplatform.backend.candidate.dto.dashboard.ReadinessPoint;
import com.interviewplatform.backend.candidate.dto.dashboard.SkillBreakdownResponse;
import com.interviewplatform.backend.model.User;
import com.interviewplatform.backend.service.UserService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import com.interviewplatform.backend.candidate.dto.dashboard.SessionResponse;

@Service
public class CandidateDashboardService {

    // User Service
    private final UserService userService;

    // Constructor
    public CandidateDashboardService(UserService userService) {
        this.userService = userService;
    }

    // Get Dashboard
    public DashboardResponse getDashboard() {

        // Logged-in User
        User user = userService.getLoggedInUser();

        // Dashboard Response
        DashboardResponse response = new DashboardResponse();

        response.setName(user.getName());
        response.setQuestionsSolved(124);
        response.setReadinessScore(88);
        response.setPracticeHours(34);
        response.setMockSessions(9);

        // Return Response
        return response;
    }

    // Readiness Chart
    public List<ReadinessPoint> getReadinessChart() {

        List<ReadinessPoint> readiness = new ArrayList<>();

        readiness.add(new ReadinessPoint("Mock 1", 55));
        readiness.add(new ReadinessPoint("Mock 2", 61));
        readiness.add(new ReadinessPoint("Mock 3", 67));
        readiness.add(new ReadinessPoint("Mock 4", 63));
        readiness.add(new ReadinessPoint("Mock 5", 74));
        readiness.add(new ReadinessPoint("Mock 6", 81));
        readiness.add(new ReadinessPoint("Mock 7", 79));
        readiness.add(new ReadinessPoint("Mock 8", 88));

        // Return Response
        return readiness;
    }

    // Skill Breakdown
    public SkillBreakdownResponse getSkillBreakdown() {

        // Skill Breakdown Response
        SkillBreakdownResponse response = new SkillBreakdownResponse();

        response.setConfidence(82);
        response.setTechnical(88);
        response.setReadiness(85);
        response.setProblemSolving(90);
        response.setCommunication(76);

        // Return Response
        return response;
    }

    // Recent & Upcoming Sessions
    public List<SessionResponse> getSessions() {

        List<SessionResponse> sessions = new ArrayList<>();

        sessions.add(new SessionResponse(
                "Technical Round",
                "Jun 12, 2026",
                "45 min",
                82,
                "COMPLETED"
        ));

        sessions.add(new SessionResponse(
                "Behavioral Round",
                "Jun 10, 2026",
                "30 min",
                76,
                "COMPLETED"
        ));

        sessions.add(new SessionResponse(
                "System Design",
                "Jun 18, 2026",
                "60 min",
                null,
                "SCHEDULED"
        ));

        // Return Response
        return sessions;
    }
}
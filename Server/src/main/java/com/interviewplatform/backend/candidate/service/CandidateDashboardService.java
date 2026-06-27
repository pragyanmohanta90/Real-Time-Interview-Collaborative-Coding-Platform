package com.interviewplatform.backend.candidate.service;

import com.interviewplatform.backend.candidate.dto.dashboard.DashboardResponse;
import com.interviewplatform.backend.model.User;
import com.interviewplatform.backend.service.UserService;
import org.springframework.stereotype.Service;
import com.interviewplatform.backend.candidate.dto.dashboard.ReadinessPoint;
import java.util.ArrayList;
import java.util.List;

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

        return readiness;
    }
}
package com.interviewplatform.backend.candidate.service;

import com.interviewplatform.backend.candidate.dto.dashboard.*;
import com.interviewplatform.backend.candidate.dto.practice.PracticeQuestionResponse;
import com.interviewplatform.backend.model.User;
import com.interviewplatform.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateDashboardService {

    // User Service
    private final UserService userService;

    // Practice Question Service
    private final PracticeQuestionService practiceQuestionService;

    // Question Submission Service
    private final QuestionSubmissionService questionSubmissionService;

    // Constructor
    public CandidateDashboardService(
            UserService userService,
            PracticeQuestionService practiceQuestionService,
            QuestionSubmissionService questionSubmissionService
    ) {
        this.userService = userService;
        this.practiceQuestionService = practiceQuestionService;
        this.questionSubmissionService = questionSubmissionService;
    }

    // Dashboard
    public DashboardResponse getDashboard() {

        User user = userService.getLoggedInUser();

        // Solved Questions
        long solvedQuestions =
                questionSubmissionService.getSolvedCount(user.getId());

        // Coding Time
        long codingTimeSeconds =
                questionSubmissionService.getTotalCodingTimeSeconds(user.getId());



        DashboardResponse response = new DashboardResponse();

        // User
        DashboardUserResponse dashboardUser = new DashboardUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getTitle(),
                user.getLocation(),
                user.getAvatar(),
                user.getAbout(),
                user.getReadinessScore()
        );

        response.setUser(dashboardUser);

        // Stats
        DashboardStatsResponse stats = new DashboardStatsResponse(
                (int) solvedQuestions,
                codingTimeSeconds,
                9,
                12
        );

        response.setStats(stats);

        // Skill Breakdown
        response.setSkillBreakdown(getSkillBreakdown());

        // Progress History
        response.setProgressHistory(getReadinessChart());

        // Practice Questions
        List<PracticeQuestionResponse> questions =
                practiceQuestionService.getPracticeQuestions(null);

        response.setPracticeQuestions(questions);

        // Mock Sessions
        response.setMockSessions(getSessions());

        // Last Mock Result
        response.setLastMockResult(new RecentSessionResponse());

        // Empty Lists
        response.setRooms(new ArrayList<>());
        response.setExperience(new ArrayList<>());

        // Dynamic Skills
        response.setSkills(user.getSkills());

        // Dynamic Targets
        response.setTargets(user.getTargets());

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

    // Skill Breakdown
    public SkillBreakdownResponse getSkillBreakdown() {

        SkillBreakdownResponse response = new SkillBreakdownResponse();

        response.setConfidence(72);
        response.setTechnical(58);
        response.setReadiness(84);
        response.setProblemSolving(90);
        response.setCommunication(78);

        return response;
    }

    // Sessions
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

        return sessions;
    }
}
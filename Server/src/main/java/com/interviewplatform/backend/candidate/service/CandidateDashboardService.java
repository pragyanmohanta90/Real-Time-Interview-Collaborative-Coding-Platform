package com.interviewplatform.backend.candidate.service;

import com.interviewplatform.backend.candidate.dto.dashboard.*;
import com.interviewplatform.backend.candidate.dto.practice.PracticeQuestionResponse;
import com.interviewplatform.backend.model.User;
import com.interviewplatform.backend.service.UserService;
import org.springframework.stereotype.Service;
import com.interviewplatform.backend.candidate.dto.dashboard.AddTargetRequest;
import com.interviewplatform.backend.candidate.dto.dashboard.TargetResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateDashboardService {

    // User Service
    private final UserService userService;

    // Practice Question Service
    private final PracticeQuestionService practiceQuestionService;

    // Constructor
    public CandidateDashboardService(
            UserService userService,
            PracticeQuestionService practiceQuestionService
    ) {
        this.userService = userService;
        this.practiceQuestionService = practiceQuestionService;
    }

    // Dashboard
    public DashboardResponse getDashboard() {

        User user = userService.getLoggedInUser();

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
                88
        );

        response.setUser(dashboardUser);

        // Stats
        DashboardStatsResponse stats = new DashboardStatsResponse(
                124,
                34,
                9,
                12
        );

        response.setStats(stats);

        // Skill Breakdown
        response.setSkillBreakdown(getSkillBreakdown());

        // Progress History
        response.setProgressHistory(getReadinessChart());

        // Practice Questions (MongoDB)
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

        // Sample Skills
        List<String> skills = new ArrayList<>();
        skills.add("Java");
        skills.add("Spring Boot");
        response.setSkills(skills);

        // Sample Targets
        List<String> targets = new ArrayList<>();
        targets.add("Google");
        targets.add("OpenAI");
        response.setTargets(targets);

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

    // Add Target
    public TargetResponse addTarget(AddTargetRequest request) {

        User user = userService.getLoggedInUser();

        String target = request.getTarget();

        if (target == null || target.trim().isEmpty()) {
            throw new RuntimeException("Target is required.");
        }

        target = target.trim();

        if (user.getTargets().contains(target)) {
            throw new RuntimeException(
                    "Target \"" + target + "\" already exists."
            );
        }

        user.getTargets().add(target);

        userService.saveUser(user);

        return new TargetResponse(
                "target_" + user.getTargets().size(),
                target,
                false
        );
    }

    // Delete Target
    public TargetResponse deleteTarget(String id) {

        User user = userService.getLoggedInUser();

        int index;

        try {
            index = Integer.parseInt(
                    id.replace("target_", "")
            ) - 1;
        } catch (Exception e) {
            throw new RuntimeException("Invalid target id.");
        }

        if (index < 0 || index >= user.getTargets().size()) {
            throw new RuntimeException("Target not found.");
        }

        String removedTarget = user.getTargets().remove(index);

        userService.saveUser(user);

        return new TargetResponse(
                id,
                removedTarget,
                true
        );
    }
}
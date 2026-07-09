package com.interviewplatform.backend.candidate.controller;

import com.interviewplatform.backend.candidate.dto.dashboard.DashboardResponse;
import com.interviewplatform.backend.candidate.dto.dashboard.ReadinessPoint;
import com.interviewplatform.backend.candidate.dto.dashboard.SessionResponse;
import com.interviewplatform.backend.candidate.dto.dashboard.SkillBreakdownResponse;
import com.interviewplatform.backend.candidate.service.CandidateDashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/candidate/dashboard")
public class CandidateDashboardController {

    // Dashboard Service
    private final CandidateDashboardService dashboardService;

    // Constructor
    public CandidateDashboardController(
            CandidateDashboardService dashboardService
    ) {
        this.dashboardService = dashboardService;
    }

    // Dashboard API
    @GetMapping
    public DashboardResponse getDashboard() {
        return dashboardService.getDashboard();
    }

    // Readiness Chart API
    @GetMapping("/readiness")
    public List<ReadinessPoint> getReadinessChart() {
        return dashboardService.getReadinessChart();
    }

    // Skill Breakdown API
    @GetMapping("/skills")
    public SkillBreakdownResponse getSkillBreakdown() {
        return dashboardService.getSkillBreakdown();
    }

    // Recent & Upcoming Sessions API
    @GetMapping("/sessions")
    public List<SessionResponse> getSessions() {
        return dashboardService.getSessions();
    }
}
package com.interviewplatform.backend.candidate.controller;

import com.interviewplatform.backend.candidate.dto.dashboard.DashboardResponse;
import com.interviewplatform.backend.candidate.dto.dashboard.ReadinessPoint;
import com.interviewplatform.backend.candidate.dto.dashboard.SessionResponse;
import com.interviewplatform.backend.candidate.dto.dashboard.SkillBreakdownResponse;
import com.interviewplatform.backend.candidate.service.CandidateDashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.interviewplatform.backend.candidate.dto.dashboard.AddTargetRequest;
import com.interviewplatform.backend.candidate.dto.dashboard.TargetResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/candidate/dashboard")
public class CandidateDashboardController {

    // Dashboard Service
    private final CandidateDashboardService dashboardService;

    // Constructor
    public CandidateDashboardController(CandidateDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // Dashboard API
    @GetMapping
    public DashboardResponse getDashboard() {

        // Return Dashboard
        return dashboardService.getDashboard();
    }

    // Readiness Chart API
    @GetMapping("/readiness")
    public List<ReadinessPoint> getReadinessChart() {

        // Return Readiness Chart
        return dashboardService.getReadinessChart();
    }

    // Skill Breakdown API
    @GetMapping("/skills")
    public SkillBreakdownResponse getSkillBreakdown() {

        // Return Skill Breakdown
        return dashboardService.getSkillBreakdown();
    }

    // Recent & Upcoming Sessions API
    @GetMapping("/sessions")
    public List<SessionResponse> getSessions() {

        // Return Sessions
        return dashboardService.getSessions();
    }

    // Add Target
    @PostMapping("/targets")
    public TargetResponse addTarget(
            @RequestBody AddTargetRequest request
    ) {
        return dashboardService.addTarget(request);
    }

    // Delete Target
    @DeleteMapping("/targets/{id}")
    public TargetResponse deleteTarget(
            @PathVariable String id
    ) {
        return dashboardService.deleteTarget(id);
    }
}
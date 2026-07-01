package com.interviewplatform.backend.candidate.controller;

import com.interviewplatform.backend.candidate.dto.dashboard.AddTargetRequest;
import com.interviewplatform.backend.candidate.dto.dashboard.TargetResponse;
import com.interviewplatform.backend.candidate.service.CandidateDashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidate/targets")
public class CandidateTargetController {

    private final CandidateDashboardService dashboardService;

    public CandidateTargetController(CandidateDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // Add Target
    @PostMapping
    public TargetResponse addTarget(
            @RequestBody AddTargetRequest request
    ) {
        return dashboardService.addTarget(request);
    }

    // Delete Target
    @DeleteMapping("/{id}")
    public TargetResponse deleteTarget(
            @PathVariable String id
    ) {
        return dashboardService.deleteTarget(id);
    }
}
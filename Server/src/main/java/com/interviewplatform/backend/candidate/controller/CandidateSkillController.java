package com.interviewplatform.backend.candidate.controller;

import com.interviewplatform.backend.candidate.dto.profile.AddSkillRequest;
import com.interviewplatform.backend.candidate.dto.profile.SkillResponse;
import com.interviewplatform.backend.candidate.service.CandidateSkillService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidate/skills")
@CrossOrigin(origins = "http://localhost:5173")
public class CandidateSkillController {

    // Skill Service
    private final CandidateSkillService candidateSkillService;

    // Constructor
    public CandidateSkillController(
            CandidateSkillService candidateSkillService
    ) {
        this.candidateSkillService = candidateSkillService;
    }

    // Add Skill
    @PostMapping
    public SkillResponse addSkill(
            @RequestBody AddSkillRequest request
    ) {
        return candidateSkillService.addSkill(request);
    }

    // Delete Skill
    @DeleteMapping("/{id}")
    public SkillResponse deleteSkill(
            @PathVariable String id
    ) {
        return candidateSkillService.deleteSkill(id);
    }
}
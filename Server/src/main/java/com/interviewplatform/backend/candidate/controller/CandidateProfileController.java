package com.interviewplatform.backend.candidate.controller;

import com.interviewplatform.backend.candidate.dto.profile.AddSkillRequest;
import com.interviewplatform.backend.candidate.dto.profile.AddTargetRequest;
import com.interviewplatform.backend.candidate.dto.profile.SkillResponse;
import com.interviewplatform.backend.candidate.dto.profile.TargetResponse;
import com.interviewplatform.backend.candidate.service.CandidateProfileService;
import com.interviewplatform.backend.dto.UpdateUserRequest;
import com.interviewplatform.backend.dto.UserResponse;
import com.interviewplatform.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidate")
public class CandidateProfileController {

    // User Service
    private final UserService userService;

    // Candidate Profile Service
    private final CandidateProfileService candidateProfileService;

    // Constructor
    public CandidateProfileController(
            UserService userService,
            CandidateProfileService candidateProfileService
    ) {
        this.userService = userService;
        this.candidateProfileService = candidateProfileService;
    }

    // Update Profile
    @PutMapping("/profile")
    public UserResponse updateProfile(
            @RequestBody UpdateUserRequest request
    ) {
        return userService.updateProfile(request);
    }

    // Add Skill
    @PostMapping("/skills")
    public SkillResponse addSkill(
            @RequestBody AddSkillRequest request
    ) {
        return candidateProfileService.addSkill(request);
    }

    // Delete Skill
    @DeleteMapping("/skills/{skill}")
    public void deleteSkill(
            @PathVariable String skill
    ) {
        candidateProfileService.deleteSkill(skill);
    }

    // Add Target
    @PostMapping("/targets")
    public TargetResponse addTarget(
            @RequestBody AddTargetRequest request
    ) {
        return candidateProfileService.addTarget(request);
    }

    // Delete Target
    @DeleteMapping("/targets/{target}")
    public void deleteTarget(
            @PathVariable String target
    ) {
        candidateProfileService.deleteTarget(target);
    }
}
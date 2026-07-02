package com.interviewplatform.backend.controller;

import com.interviewplatform.backend.dto.ProfileResponse;
import com.interviewplatform.backend.dto.UpdateUserRequest;
import com.interviewplatform.backend.service.ProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "*")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // GET Profile
    @GetMapping
    public ProfileResponse getProfile(
            @RequestParam String email
    ) {
        return profileService.getProfile(email);
    }

    // UPDATE Profile
    @PutMapping
    public ProfileResponse updateProfile(
            @RequestParam String email,
            @RequestBody UpdateUserRequest request
    ) {
        return profileService.updateProfile(email, request);
    }

    // ADD Skill
    @PostMapping("/skills")
    public String addSkill(
            @RequestParam String email,
            @RequestParam String skill
    ) {
        return profileService.addSkill(email, skill);
    }

    // DELETE Skill
    @DeleteMapping("/skills")
    public void deleteSkill(
            @RequestParam String email,
            @RequestParam String skill
    ) {
        profileService.deleteSkill(email, skill);
    }

    // ADD Target
    @PostMapping("/targets")
    public String addTarget(
            @RequestParam String email,
            @RequestParam String target
    ) {
        return profileService.addTarget(email, target);
    }

    // DELETE Target
    @DeleteMapping("/targets")
    public void deleteTarget(
            @RequestParam String email,
            @RequestParam String target
    ) {
        profileService.deleteTarget(email, target);
    }
}
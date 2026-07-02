package com.interviewplatform.backend.candidate.controller;

import com.interviewplatform.backend.dto.UpdateUserRequest;
import com.interviewplatform.backend.dto.UserResponse;
import com.interviewplatform.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidate/profile")
@CrossOrigin(origins = "http://localhost:5173")
public class CandidateProfileController {

    // User Service
    private final UserService userService;

    // Constructor
    public CandidateProfileController(UserService userService) {
        this.userService = userService;
    }

    // Get Candidate Profile
    @GetMapping
    public UserResponse getProfile() {
        return userService.getCurrentUser();
    }

    // Update Candidate Profile
    @PutMapping
    public UserResponse updateProfile(
            @RequestBody UpdateUserRequest request
    ) {
        return userService.updateProfile(request);
    }
}
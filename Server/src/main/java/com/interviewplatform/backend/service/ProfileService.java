package com.interviewplatform.backend.service;

import com.interviewplatform.backend.dto.ProfileResponse;
import com.interviewplatform.backend.dto.UpdateUserRequest;

public interface ProfileService {

    ProfileResponse getProfile(String email);

    ProfileResponse updateProfile(String email, UpdateUserRequest request);

    String addSkill(String email, String skill);

    void deleteSkill(String email, String skill);

    String addTarget(String email, String target);

    void deleteTarget(String email, String target);
}
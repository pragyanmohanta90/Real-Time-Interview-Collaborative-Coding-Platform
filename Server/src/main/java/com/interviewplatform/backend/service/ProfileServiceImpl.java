package com.interviewplatform.backend.service;

import com.interviewplatform.backend.dto.ProfileResponse;
import com.interviewplatform.backend.dto.UpdateUserRequest;
import com.interviewplatform.backend.model.User;
import com.interviewplatform.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    public ProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ProfileResponse getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new ProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getTitle(),
                user.getLocation(),
                user.getAvatar(),
                user.getAbout(),
                user.getReadinessScore(),
                user.getSkills(),
                user.getTargets(),
                user.getExperience()
        );
    }

    @Override
    public ProfileResponse updateProfile(String email, UpdateUserRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setTitle(request.getTitle());
        user.setLocation(request.getLocation());
        user.setAbout(request.getAbout());

        userRepository.save(user);

        return getProfile(user.getEmail());
    }

    @Override
    public String addSkill(String email, String skill) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getSkills() == null) {
            user.setSkills(new ArrayList<>());
        }

        if (!user.getSkills().contains(skill)) {
            user.getSkills().add(skill);
        }

        userRepository.save(user);

        return skill;
    }

    @Override
    public void deleteSkill(String email, String skill) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getSkills().remove(skill);

        userRepository.save(user);
    }

    @Override
    public String addTarget(String email, String target) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getTargets() == null) {
            user.setTargets(new ArrayList<>());
        }

        if (!user.getTargets().contains(target)) {
            user.getTargets().add(target);
        }

        userRepository.save(user);

        return target;
    }

    @Override
    public void deleteTarget(String email, String target) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getTargets().remove(target);

        userRepository.save(user);
    }
}
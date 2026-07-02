package com.interviewplatform.backend.candidate.service;

import com.interviewplatform.backend.candidate.dto.profile.AddSkillRequest;
import com.interviewplatform.backend.candidate.dto.profile.SkillResponse;
import com.interviewplatform.backend.model.User;
import com.interviewplatform.backend.repository.UserRepository;
import com.interviewplatform.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateSkillService {

    // User Repository
    private final UserRepository userRepository;

    // User Service
    private final UserService userService;

    // Constructor
    public CandidateSkillService(
            UserRepository userRepository,
            UserService userService
    ) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // Add Skill
    public SkillResponse addSkill(AddSkillRequest request) {

        if (request.getSkill() == null || request.getSkill().isBlank()) {
            throw new RuntimeException("skill is required.");
        }

        User user = userService.getLoggedInUser();

        List<String> skills = user.getSkills();

        for (String skill : skills) {
            if (skill.equalsIgnoreCase(request.getSkill())) {
                throw new RuntimeException(
                        "Skill \"" + request.getSkill() + "\" already exists."
                );
            }
        }

        skills.add(request.getSkill());

        userRepository.save(user);

        return new SkillResponse(
                "sk_" + skills.size(),
                request.getSkill()
        );
    }

    // Delete Skill
    public SkillResponse deleteSkill(String id) {

        User user = userService.getLoggedInUser();

        List<String> skills = user.getSkills();

        String removedSkill = null;

        if (id.startsWith("sk_")) {

            int index = Integer.parseInt(id.substring(3)) - 1;

            if (index >= 0 && index < skills.size()) {
                removedSkill = skills.remove(index);
            }

        } else {

            for (String skill : skills) {

                if (skill.equalsIgnoreCase(id)) {
                    removedSkill = skill;
                    skills.remove(skill);
                    break;
                }
            }
        }

        if (removedSkill == null) {
            throw new RuntimeException(
                    "Skill \"" + id + "\" not found."
            );
        }

        userRepository.save(user);

        return new SkillResponse(
                id,
                removedSkill,
                true
        );
    }
}
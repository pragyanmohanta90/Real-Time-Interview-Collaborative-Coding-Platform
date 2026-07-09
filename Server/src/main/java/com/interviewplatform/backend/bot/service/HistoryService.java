package com.interviewplatform.backend.bot.service;

import com.interviewplatform.backend.bot.entity.Interview;
import com.interviewplatform.backend.bot.exception.ApiException;
import com.interviewplatform.backend.bot.repository.AIInterviewRepository;
import com.interviewplatform.backend.model.User;
import com.interviewplatform.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final AIInterviewRepository interviewRepository;
    private final UserService userService;

    // Identity comes from the JWT (SecurityContext), never from a client-supplied id.
    public List<Interview> getHistoryForCurrentUser() {
        User currentUser = userService.getLoggedInUser();
        return interviewRepository.findByUserIdOrderByCreatedAtDesc(currentUser.getId());
    }

    public Interview getInterviewByIdForCurrentUser(String id) {
        User currentUser = userService.getLoggedInUser();

        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ApiException("Interview not found", HttpStatus.NOT_FOUND));

        if (!interview.getUserId().equals(currentUser.getId())) {
            // Don't reveal that the interview exists — same response as "not found".
            throw new ApiException("Interview not found", HttpStatus.NOT_FOUND);
        }

        return interview;
    }
}

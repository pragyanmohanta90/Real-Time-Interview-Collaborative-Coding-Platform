package com.interviewplatform.backend.bot.controller;

import com.interviewplatform.backend.bot.entity.Interview;
import com.interviewplatform.backend.bot.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    // Identity comes from the JWT (SecurityContext), never from the client.
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getHistory() {

        List<Map<String, Object>> summaries = historyService.getHistoryForCurrentUser().stream()
                .map(interview -> {
                    Map<String, Object> summary = new java.util.LinkedHashMap<>();
                    summary.put("id", interview.getId());
                    summary.put("role", interview.getRole());
                    summary.put("difficulty", interview.getDifficulty());
                    summary.put("score", interview.getEvaluation() != null
                            ? interview.getEvaluation().getOverallScore() : 0);
                    summary.put("createdAt", interview.getCreatedAt());
                    return summary;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interview> getInterview(@PathVariable String id) {
        return ResponseEntity.ok(historyService.getInterviewByIdForCurrentUser(id));
    }
}

package com.interviewplatform.backend.bot.dto;

import com.interviewplatform.backend.bot.entity.Interview;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Returned after POST /api/interview/evaluate — wraps the saved interview id
 * plus the AI evaluation so the frontend can route straight to the report.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationResponse {
    private String interviewId;
    private Interview.Evaluation evaluation;
}

package com.interviewplatform.backend.bot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "interviews")
public class Interview {

    @Id
    private String id;

    private String userId;

    private String role;
    private String experience;
    private String difficulty;
    private int questionCount;

    private List<String> questions;

    /** Keyed by question index as a string, e.g. "1", "2" */
    private Map<String, String> answers;

    private Evaluation evaluation;

    private Instant createdAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Evaluation {
        private int overallScore;
        private int technicalScore;
        private int communicationScore;
        private int problemSolvingScore;
        private int confidenceScore;

        private List<String> strengths;
        private List<String> weaknesses;
        private List<String> improvements;

        private List<QuestionAnalysis> questionAnalysis;

        private String hiringRecommendation;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionAnalysis {
        private String question;
        private String answer;
        private String feedback;
        private int score;
    }
}

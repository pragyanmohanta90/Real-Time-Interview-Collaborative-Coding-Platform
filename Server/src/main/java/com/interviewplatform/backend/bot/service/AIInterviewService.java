package com.interviewplatform.backend.bot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.interviewplatform.backend.bot.dto.EvaluateRequest;
import com.interviewplatform.backend.bot.dto.EvaluationResponse;
import com.interviewplatform.backend.bot.dto.GenerateRequest;
import com.interviewplatform.backend.bot.entity.Interview;
import com.interviewplatform.backend.bot.exception.ApiException;
import com.interviewplatform.backend.bot.repository.AIInterviewRepository;
import com.interviewplatform.backend.model.User;
import com.interviewplatform.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AIInterviewService {

    private final GeminiService geminiService;
    private final AIInterviewRepository interviewRepository;
    private final UserService userService;

    public List<String> generateQuestions(GenerateRequest request) {
        JsonNode result = geminiService.generateQuestions(
                request.getRole(),
                request.getExperience(),
                request.getDifficulty(),
                request.getQuestionCount()
        );

        JsonNode questionsNode = result.path("questions");
        if (!questionsNode.isArray() || questionsNode.isEmpty()) {
            throw new ApiException("AI did not return any questions", HttpStatus.BAD_GATEWAY);
        }

        List<String> questions = new ArrayList<>();
        for (JsonNode q : questionsNode) {
            questions.add(q.asText());
        }
        return questions;
    }

    public EvaluationResponse evaluateInterview(EvaluateRequest request) {
        JsonNode result = geminiService.evaluateAnswers(
                request.getRole(),
                request.getExperience(),
                request.getDifficulty(),
                request.getQuestions(),
                request.getAnswers() != null ? request.getAnswers() : java.util.Collections.emptyMap()
        );

        Interview.Evaluation evaluation = mapEvaluation(result);

        // Identity comes ONLY from the authenticated JWT — never from the
        // request body — so a user can never save/read another user's data.
        User currentUser = userService.getLoggedInUser();

        Interview interview = new Interview();
        interview.setUserId(currentUser.getId());
        interview.setRole(request.getRole());
        interview.setExperience(request.getExperience());
        interview.setDifficulty(request.getDifficulty());
        interview.setQuestionCount(request.getQuestions().size());
        interview.setQuestions(request.getQuestions());
        interview.setAnswers(request.getAnswers());
        interview.setEvaluation(evaluation);
        interview.setCreatedAt(Instant.now());

        Interview saved = interviewRepository.save(interview);

        return new EvaluationResponse(saved.getId(), saved.getEvaluation());
    }

    private Interview.Evaluation mapEvaluation(JsonNode node) {
        Interview.Evaluation evaluation = new Interview.Evaluation();

        evaluation.setOverallScore(node.path("overall_score").asInt(0));
        evaluation.setTechnicalScore(node.path("technical_score").asInt(0));
        evaluation.setCommunicationScore(node.path("communication_score").asInt(0));
        evaluation.setProblemSolvingScore(node.path("problem_solving_score").asInt(0));
        evaluation.setConfidenceScore(node.path("confidence_score").asInt(0));

        evaluation.setStrengths(toStringList(node.path("strengths")));
        evaluation.setWeaknesses(toStringList(node.path("weaknesses")));
        evaluation.setImprovements(toStringList(node.path("improvements")));
        evaluation.setHiringRecommendation(node.path("hiring_recommendation").asText("Borderline"));

        List<Interview.QuestionAnalysis> analysisList = new ArrayList<>();
        JsonNode analysisNode = node.path("question_analysis");
        if (analysisNode.isArray()) {
            for (JsonNode item : analysisNode) {
                Interview.QuestionAnalysis qa = new Interview.QuestionAnalysis();
                qa.setQuestion(item.path("question").asText(""));
                qa.setAnswer(item.path("answer").asText(""));
                qa.setFeedback(item.path("feedback").asText(""));
                qa.setScore(item.path("score").asInt(0));
                analysisList.add(qa);
            }
        }
        evaluation.setQuestionAnalysis(analysisList);

        return evaluation;
    }

    private List<String> toStringList(JsonNode node) {
        List<String> list = new ArrayList<>();
        if (node.isArray()) {
            for (JsonNode item : node) {
                list.add(item.asText());
            }
        }
        return list;
    }
}

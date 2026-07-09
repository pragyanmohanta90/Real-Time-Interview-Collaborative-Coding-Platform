package com.interviewplatform.backend.bot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewplatform.backend.bot.exception.ApiException;
import com.interviewplatform.backend.bot.util.GeminiJsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Wraps all calls to the Google Gemini API. Per the project's cost rule,
 * Gemini is invoked at most TWICE per interview:
 *   1) generateQuestions() — once, to produce the full question list
 *   2) evaluateAnswers()   — once, to score the whole interview
 * There are no per-question API calls anywhere in this service.
 */
@Service
@RequiredArgsConstructor
public class GeminiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GeminiJsonParser jsonParser;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public JsonNode generateQuestions(String role, String experience, String difficulty, int questionCount) {
        String prompt = """
                You are an expert technical interviewer. Generate exactly %d interview questions
                for a candidate applying for the role of "%s" with %s of experience,
                at a "%s" difficulty level.

                Mix conceptual, practical and (if relevant to the role) coding/scenario questions.
                Do not number the questions yourself.

                Respond with ONLY valid JSON in this exact shape, no markdown fences, no extra text:
                {
                  "questions": ["question 1 text", "question 2 text", "..."]
                }
                """.formatted(questionCount, role, experience, difficulty);

        String rawText = callGemini(prompt);
        return jsonParser.parse(rawText);
    }

    public JsonNode evaluateAnswers(String role, String experience, String difficulty,
                                     java.util.List<String> questions, Map<String, String> answers) {

        StringBuilder qaBlock = new StringBuilder();
        for (int i = 0; i < questions.size(); i++) {
            String key = String.valueOf(i + 1);
            String answer = answers.getOrDefault(key, "(no answer provided)");
            qaBlock.append("Q").append(i + 1).append(": ").append(questions.get(i)).append("\n");
            qaBlock.append("A").append(i + 1).append(": ").append(answer).append("\n\n");
        }

        String prompt = """
                You are an expert technical interview evaluator. A candidate interviewed for the role
                of "%s" with %s of experience, at "%s" difficulty. Evaluate their full set of
                question/answer pairs below.

                %s

                Score each category from 0-100 and provide an overall assessment.

                Respond with ONLY valid JSON in this exact shape, no markdown fences, no extra text:
                {
                  "overall_score": 0,
                  "technical_score": 0,
                  "communication_score": 0,
                  "problem_solving_score": 0,
                  "confidence_score": 0,
                  "strengths": ["..."],
                  "weaknesses": ["..."],
                  "improvements": ["..."],
                  "question_analysis": [
                    {"question": "...", "answer": "...", "feedback": "...", "score": 0}
                  ],
                  "hiring_recommendation": "Recommended | Not Recommended | Borderline"
                }
                """.formatted(role, experience, difficulty, qaBlock.toString().trim());

        String rawText = callGemini(prompt);
        return jsonParser.parse(rawText);
    }

    private String callGemini(String prompt) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new ApiException("Gemini API key is not configured on the server", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Map<String, Object> part = new LinkedHashMap<>();
        part.put("text", prompt);

        Map<String, Object> content = new LinkedHashMap<>();
        content.put("parts", new Object[]{part});

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("contents", new Object[]{content});

        Map<String, Object> generationConfig = new LinkedHashMap<>();
        generationConfig.put("temperature", 0.7);
        generationConfig.put("responseMimeType", "application/json");
        body.put("generationConfig", generationConfig);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", apiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity(apiUrl, entity, String.class);
        } catch (RestClientException e) {
            throw new ApiException("Failed to reach Gemini API: " + e.getMessage(), HttpStatus.BAD_GATEWAY);
        }

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ApiException("Gemini API returned an error response", HttpStatus.BAD_GATEWAY);
        }

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode textNode = root
                    .path("candidates").path(0)
                    .path("content").path("parts").path(0)
                    .path("text");

            if (textNode.isMissingNode() || textNode.asText().isBlank()) {
                throw new ApiException("Gemini API response did not contain text content", HttpStatus.BAD_GATEWAY);
            }

            return textNode.asText();
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException("Failed to parse Gemini API response envelope: " + e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}

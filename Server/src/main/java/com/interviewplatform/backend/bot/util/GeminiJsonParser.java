package com.interviewplatform.backend.bot.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewplatform.backend.bot.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Gemini sometimes wraps JSON responses in markdown code fences (```json ... ```)
 * or adds stray text before/after the JSON object. This utility safely
 * extracts and parses the JSON payload so a single malformed response never
 * crashes the request.
 */
@Component
public class GeminiJsonParser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode parse(String rawText) {
        if (rawText == null || rawText.isBlank()) {
            throw new ApiException("Empty response received from AI model", HttpStatus.BAD_GATEWAY);
        }

        String cleaned = stripCodeFences(rawText.trim());
        String jsonSlice = extractJsonObjectOrArray(cleaned);

        try {
            return objectMapper.readTree(jsonSlice);
        } catch (Exception e) {
            throw new ApiException(
                    "Failed to parse AI response as JSON: " + e.getMessage(),
                    HttpStatus.BAD_GATEWAY
            );
        }
    }

    private String stripCodeFences(String text) {
        String result = text;
        if (result.startsWith("```")) {
            int firstNewline = result.indexOf('\n');
            if (firstNewline != -1) {
                result = result.substring(firstNewline + 1);
            }
        }
        if (result.endsWith("```")) {
            result = result.substring(0, result.lastIndexOf("```"));
        }
        return result.trim();
    }

    /**
     * Finds the first balanced { ... } or [ ... ] block in the text, in case
     * the model added preamble/closing remarks around the JSON.
     */
    private String extractJsonObjectOrArray(String text) {
        int objStart = text.indexOf('{');
        int arrStart = text.indexOf('[');

        if (objStart == -1 && arrStart == -1) {
            throw new ApiException("No JSON object found in AI response", HttpStatus.BAD_GATEWAY);
        }

        boolean useObject = objStart != -1 && (arrStart == -1 || objStart < arrStart);
        char open = useObject ? '{' : '[';
        char close = useObject ? '}' : ']';
        int start = useObject ? objStart : arrStart;

        int depth = 0;
        for (int i = start; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == open) depth++;
            if (c == close) depth--;
            if (depth == 0) {
                return text.substring(start, i + 1);
            }
        }

        throw new ApiException("Unbalanced JSON structure in AI response", HttpStatus.BAD_GATEWAY);
    }
}

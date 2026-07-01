package com.interviewplatform.backend.candidate.service;

import com.interviewplatform.backend.candidate.dto.practice.PracticeQuestionResponse;
import org.springframework.stereotype.Service;
import com.interviewplatform.backend.candidate.dto.practice.SubmissionResponse;
import java.util.ArrayList;
import java.util.List;
import com.interviewplatform.backend.candidate.dto.practice.PracticeQuestionDetailResponse;

@Service
public class PracticeQuestionService {

    // Get Practice Questions
    public List<PracticeQuestionResponse> getPracticeQuestions(String difficulty) {

        List<PracticeQuestionResponse> questions = new ArrayList<>();

        questions.add(new PracticeQuestionResponse(
                "1",
                "Merge K Sorted Lists",
                "Linked Lists",
                "Hard",
                false
        ));

        questions.add(new PracticeQuestionResponse(
                "2",
                "Design Twitter",
                "System Design",
                "Hard",
                false
        ));

        questions.add(new PracticeQuestionResponse(
                "3",
                "Word Search II",
                "Backtracking",
                "Hard",
                false
        ));

        questions.add(new PracticeQuestionResponse(
                "4",
                "Two Sum",
                "Arrays",
                "Easy",
                true
        ));

        questions.add(new PracticeQuestionResponse(
                "5",
                "Valid Parentheses",
                "Stacks",
                "Easy",
                true
        ));

        questions.add(new PracticeQuestionResponse(
                "6",
                "Binary Search",
                "Searching",
                "Easy",
                true
        ));

        questions.add(new PracticeQuestionResponse(
                "7",
                "Longest Substring Without Repeating Characters",
                "Strings",
                "Medium",
                true
        ));

        questions.add(new PracticeQuestionResponse(
                "8",
                "LRU Cache",
                "Design",
                "Medium",
                false
        ));

        // Return All Questions
        if (difficulty == null || difficulty.isBlank()) {
            return questions;
        }

        // Filter Questions
        List<PracticeQuestionResponse> filteredQuestions = new ArrayList<>();

        for (PracticeQuestionResponse question : questions) {

            if (question.getDifficulty().equalsIgnoreCase(difficulty)) {
                filteredQuestions.add(question);
            }
        }

        // Return Filtered Questions
        return filteredQuestions;
    }

    // Get Practice Progress
    public SubmissionResponse getPracticeProgress() {

        SubmissionResponse response = new SubmissionResponse();

        response.setCompleted(4);
        response.setTotal(8);

        // Return Response
        return response;
    }

    // Get Question By ID
    public PracticeQuestionDetailResponse getQuestionById(String id) {

        PracticeQuestionDetailResponse response = new PracticeQuestionDetailResponse();

        response.setId(id);
        response.setTitle("Two Sum");
        response.setCategory("Arrays");
        response.setDifficulty("Easy");

        response.setDescription(
                "Given an array of integers nums and an integer target, " +
                        "return indices of the two numbers such that they add up to the target."
        );

        response.setExamples(List.of(
                "Input: nums = [2,7,11,15], target = 9\nOutput: [0,1]",
                "Input: nums = [3,2,4], target = 6\nOutput: [1,2]"
        ));

        response.setConstraints(List.of(
                "2 <= nums.length <= 10^4",
                "-10^9 <= nums[i] <= 10^9",
                "Only one valid answer exists."
        ));

        response.setTags(List.of(
                "Array",
                "Hash Table"
        ));

        response.setStarterCode(
                "public class Solution {\n" +
                        "    public int[] twoSum(int[] nums, int target) {\n" +
                        "\n" +
                        "    }\n" +
                        "}"
        );

        // Return Response
        return response;
    }
}
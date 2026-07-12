package com.interviewplatform.backend.candidate.service;

import com.interviewplatform.backend.model.QuestionSubmission;
import com.interviewplatform.backend.model.SubmissionStatus;
import com.interviewplatform.backend.repository.QuestionSubmissionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class QuestionSubmissionService {

    private final QuestionSubmissionRepository questionSubmissionRepository;

    public QuestionSubmissionService(
            QuestionSubmissionRepository questionSubmissionRepository
    ) {
        this.questionSubmissionRepository = questionSubmissionRepository;
    }

    public QuestionSubmission saveSubmission(
            String userId,
            String questionId,
            String language,
            String code,
            int passedTestCases,
            int totalTestCases,
            long codingTimeSeconds
    ) {

        Optional<QuestionSubmission> existing =
                questionSubmissionRepository.findByUserIdAndQuestionId(
                        userId,
                        questionId
                );

        QuestionSubmission submission = existing.orElse(new QuestionSubmission());

        submission.setUserId(userId);
        submission.setQuestionId(questionId);
        submission.setLanguage(language);
        submission.setCode(code);
        submission.setCodingTimeSeconds(
                submission.getCodingTimeSeconds() + codingTimeSeconds
        );

        submission.setPassedTestCases(passedTestCases);
        submission.setTotalTestCases(totalTestCases);

        if (passedTestCases == totalTestCases) {
            submission.setStatus(SubmissionStatus.SOLVED);
        } else {
            submission.setStatus(SubmissionStatus.ATTEMPTED);
        }

        submission.setSubmittedAt(LocalDateTime.now());

        return questionSubmissionRepository.save(submission);
    }

    public boolean isSolved(String userId, String questionId) {

        return questionSubmissionRepository
                .findByUserIdAndQuestionId(userId, questionId)
                .map(s -> s.getStatus() == SubmissionStatus.SOLVED)
                .orElse(false);
    }

    public long getSolvedCount(String userId) {

        return questionSubmissionRepository
                .findByUserId(userId)
                .stream()
                .filter(s -> s.getStatus() == SubmissionStatus.SOLVED)
                .count();
    }

    // Total Coding Time
    public long getTotalCodingTimeSeconds(String userId) {

        Long totalCodingTime =
                questionSubmissionRepository.getTotalCodingTimeSeconds(userId);

        return totalCodingTime == null ? 0L : totalCodingTime;
    }
}
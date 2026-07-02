package com.interviewplatform.backend.repository;

import com.interviewplatform.backend.model.QuestionSubmission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionSubmissionRepository extends MongoRepository<QuestionSubmission, String> {

    List<QuestionSubmission> findByUserId(String userId);

    List<QuestionSubmission> findByQuestionId(String questionId);

    Optional<QuestionSubmission> findByUserIdAndQuestionId(String userId, String questionId);

}
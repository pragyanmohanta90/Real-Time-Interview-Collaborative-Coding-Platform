package com.interviewplatform.backend.repository;

import com.interviewplatform.backend.model.Difficulty;
import com.interviewplatform.backend.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends MongoRepository<Question, String> {

    // Find by MongoDB ID
    Optional<Question> findById(String id);

    // Find by External Problem ID
    Optional<Question> findByExternalProblemId(String externalProblemId);

    // Find by Difficulty
    List<Question> findByDifficulty(Difficulty difficulty);

    // Find by Category
    List<Question> findByCategoryIgnoreCase(String category);

}
package com.interviewplatform.backend.repository;

import com.interviewplatform.backend.model.Difficulty;
import com.interviewplatform.backend.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {

    List<Question> findByDifficulty(Difficulty difficulty);

    List<Question> findByCategory(String category);

}
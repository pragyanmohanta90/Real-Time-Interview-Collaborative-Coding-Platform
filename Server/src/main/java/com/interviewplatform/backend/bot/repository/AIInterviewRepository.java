package com.interviewplatform.backend.bot.repository;

import com.interviewplatform.backend.bot.entity.Interview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AIInterviewRepository extends MongoRepository<Interview, String> {
    List<Interview> findByUserIdOrderByCreatedAtDesc(String userId);
}

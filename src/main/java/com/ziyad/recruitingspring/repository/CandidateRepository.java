package com.ziyad.recruitingspring.repository;

import com.ziyad.recruitingspring.model.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends MongoRepository<Candidate, String> {
}
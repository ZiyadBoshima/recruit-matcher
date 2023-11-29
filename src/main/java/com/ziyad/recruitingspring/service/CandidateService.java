package com.ziyad.recruitingspring.service;

import com.ziyad.recruitingspring.model.Candidate;
import com.ziyad.recruitingspring.repository.CandidateRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public Candidate save(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public List<Candidate> getAllCandidates() {
       return candidateRepository.findAll();
    }

    public Optional<Candidate> getCandidate(ObjectId id) {
        return candidateRepository.findById(String.valueOf(id));
    }

    public Candidate addCandidate(Candidate candidate) {
        return candidateRepository.insert(candidate);
    }
}
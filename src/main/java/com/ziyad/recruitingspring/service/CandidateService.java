package com.ziyad.recruitingspring.service;

import com.ziyad.recruitingspring.model.Candidate;
import com.ziyad.recruitingspring.repository.CandidateRepository;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    private final DataService dataService;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository, DataService dataService) {
        this.candidateRepository = candidateRepository;
        this.dataService = dataService;
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

    public ResponseEntity<String> addCandidateWithResume(String options, MultipartFile resume) {
        String resumeText = dataService.convertDocToText(resume);
        ResponseEntity<String> responseEntity = dataService.extractJson(options, resumeText);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Candidate candidate = new Candidate(new JSONObject(responseEntity.getBody()));
            //TODO: Add error handling for addCandidate function
            return new ResponseEntity<String>("Upload successful: " + addCandidate(candidate), HttpStatus.OK);
        } else {
            return responseEntity;
        }
    }
}
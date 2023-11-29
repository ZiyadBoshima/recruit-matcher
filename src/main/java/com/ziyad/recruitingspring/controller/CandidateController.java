package com.ziyad.recruitingspring.controller;

import com.ziyad.recruitingspring.model.Candidate;
import com.ziyad.recruitingspring.service.CandidateService;
import com.ziyad.recruitingspring.service.DataService;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private DataService dataService;

    @GetMapping("/all")
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        return new ResponseEntity<List<Candidate>>(candidateService.getAllCandidates(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Candidate>> getCandidate(@PathVariable ObjectId id) {
        return new ResponseEntity<Optional<Candidate>>(candidateService.getCandidate(id), HttpStatus.OK);
    }

    @PostMapping("/add-candidate")
    public ResponseEntity<Candidate> addCandidate(@RequestBody Candidate candidate) {
        return new ResponseEntity<Candidate>(candidateService.addCandidate(candidate), HttpStatus.OK);
    }

    @PostMapping("/upload-resume")
    public ResponseEntity<String> uploadResume(@RequestParam(name = "file", required = true) MultipartFile file) {
        try {
            String resumeText = dataService.convertDocToText(file);
            String options = "the name, skills, and years of experience(labeled yearsOfExperience, which represents professional experience only and is indicated by one integer value. Years of experience is not an array).";
            ResponseEntity<String> responseEntity = dataService.extractJson(options, resumeText);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                // Move to data service
                Candidate candidate = new Candidate(new JSONObject(responseEntity.getBody()));
                //TODO: Add error handling for addCandidate function
                return new ResponseEntity<String>("Upload successful: " + candidateService.addCandidate(candidate), HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(responseEntity.getBody(), responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("An error occurred during processing.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

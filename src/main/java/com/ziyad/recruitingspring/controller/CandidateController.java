package com.ziyad.recruitingspring.controller;

import com.ziyad.recruitingspring.model.Candidate;
import com.ziyad.recruitingspring.service.CandidateService;
import com.ziyad.recruitingspring.service.DocumentService;
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
    private DocumentService documentService;

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
    public ResponseEntity<String> uploadResume(@RequestParam(name = "file", required = false) MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                String resumeText = documentService.convertDocToText(file);
                String options = "the name, skills, and years of experience(labeled yearsOfExperience, which represents professional experience only and is indicated by one integer value. Years of experience is not an array).";
                ResponseEntity<String> responseEntity = documentService.extractJson(options, resumeText);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    Candidate candidate = new Candidate(new JSONObject(responseEntity.getBody()));
                    //TODO: Add error handling for addCandidate function
                    return new ResponseEntity<String>("Upload successful: " + candidateService.addCandidate(candidate), HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>(responseEntity.getBody(), responseEntity.getStatusCode());
                }
            } else {
                return new ResponseEntity<String>("File is empty!", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("An error occurred during processing.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

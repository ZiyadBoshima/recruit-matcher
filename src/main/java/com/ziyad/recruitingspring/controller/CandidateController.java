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

import static com.ziyad.recruitingspring.constants.Constants.RESUME_PROCESSING_OPTIONS;

@RestController
@RequestMapping("/candidates")
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

    @PostMapping("/")
    public ResponseEntity<Candidate> addCandidate(@RequestBody Candidate candidate) {
        return new ResponseEntity<Candidate>(candidateService.addCandidate(candidate), HttpStatus.OK);
    }

    @PostMapping("/upload-resume")
    public ResponseEntity<String> uploadResume(@RequestParam(name = "file", required = true) MultipartFile file) {
        try {
            return candidateService.addCandidateWithResume(RESUME_PROCESSING_OPTIONS, file);
        } catch (Exception e) {
            return new ResponseEntity<String>("An error occurred during processing: \n" + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
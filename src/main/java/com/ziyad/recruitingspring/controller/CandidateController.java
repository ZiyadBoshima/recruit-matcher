package com.ziyad.recruitingspring.controller;

import com.ziyad.recruitingspring.model.Candidate;
import com.ziyad.recruitingspring.service.CandidateService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    @Autowired
    private CandidateService candidateService;

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

}

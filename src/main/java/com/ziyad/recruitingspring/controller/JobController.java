package com.ziyad.recruitingspring.controller;

import com.ziyad.recruitingspring.exceptions.DatabaseAccessException;
import com.ziyad.recruitingspring.exceptions.job.DuplicateJobException;
import com.ziyad.recruitingspring.exceptions.job.JobNotFoundException;
import com.ziyad.recruitingspring.model.Job;
import com.ziyad.recruitingspring.service.JobService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    JobService jobService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable ObjectId id) {
        try {
            Job job = jobService.getJobById(id);
            return ResponseEntity.ok(job);
        } catch (JobNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DatabaseAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("submit-job")
    public ResponseEntity<String> submitJob(@RequestBody Job job) {
        try {
            Job submittedJob = jobService.addJob(job);
            String successMessage = "Job titled " + submittedJob.getTitle() + " has been successfully submitted!";
            return ResponseEntity.ok(successMessage);
        } catch (DuplicateJobException e) {
            // exception is not being triggered
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DatabaseAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/update-job")
    public ResponseEntity<String> updateJob(@RequestBody Job job) {
        try {
            Job updatedJob = jobService.updateJob(job);
            String successMessage = "Job title " + updatedJob.getTitle() + " has been successfully updated!";
            return ResponseEntity.ok(successMessage);
        } catch (JobNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DatabaseAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (MappingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeJobById(@PathVariable ObjectId id) {
        try {
            jobService.removeJobById(id);
            return ResponseEntity.ok().body("Job removed successfully");
        } catch (JobNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DatabaseAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

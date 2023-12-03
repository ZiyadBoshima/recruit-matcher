package com.ziyad.recruitingspring.service;

import com.mongodb.DuplicateKeyException;
import com.ziyad.recruitingspring.exceptions.DatabaseAccessException;
import com.ziyad.recruitingspring.exceptions.job.DuplicateJobException;
import com.ziyad.recruitingspring.exceptions.job.JobNotFoundException;
import com.ziyad.recruitingspring.model.Job;
import com.ziyad.recruitingspring.repository.JobRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mapping.MappingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> getAllJobs() {
        try {
            return jobRepository.findAll();
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Error accessing the database.", e);
        }
    }

    public Job getJobById(ObjectId id) {
        try {
            return jobRepository.findById(String.valueOf(id)).orElseThrow(() -> new JobNotFoundException("Could not find job with the provided ID."));
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Error accessing the database.", e);
        }
    }

    public Job addJob(Job job) {
        try {
            return jobRepository.insert(job);
        } catch (DuplicateKeyException e) {
            throw new DuplicateJobException("Job with the same ID already exists.", e);
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Error accessing the database.", e);
        }
    }

    public void removeJobById(ObjectId id) {
        try {
            jobRepository.deleteById(id.toHexString());
        } catch (EmptyResultDataAccessException e) {
            throw new JobNotFoundException("Could not delete job with invalid ID.", e);
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Error accessing the database.", e);
        }
    }

    public Job updateJob(Job job) {
        try {
            if (job.getId() != null && jobRepository.findById(job.getId().toString()).isPresent()) {
                return jobRepository.save(job);
            } else {
                throw new JobNotFoundException("Could not update job that does not exist.");
            }
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Error accessing the database.", e);
        } catch (MappingException e) {
            throw new MappingException("Error mapping job with a document in the database.", e);
        }
    }
}

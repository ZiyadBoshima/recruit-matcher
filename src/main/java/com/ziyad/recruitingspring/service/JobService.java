package com.ziyad.recruitingspring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DuplicateKeyException;
import com.ziyad.recruitingspring.exceptions.DatabaseAccessException;
import com.ziyad.recruitingspring.exceptions.job.DuplicateJobException;
import com.ziyad.recruitingspring.exceptions.job.JobNotFoundException;
import com.ziyad.recruitingspring.model.Job;
import com.ziyad.recruitingspring.model.chatgpt.GPTResponse;
import com.ziyad.recruitingspring.repository.JobRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mapping.MappingException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ziyad.recruitingspring.constants.Constants.CANDIDATE_RANKING_OPTIONS;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CandidateService candidateService;
    private final GPTService gptService;

    @Autowired
    public JobService(JobRepository jobRepository, CandidateService candidateService, GPTService gptService) {
        this.jobRepository = jobRepository;
        this.candidateService = candidateService;
        this.gptService = gptService;
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

    public String rankCandidates(String jobDescription) throws Exception {
        String string1 = "Here is the job description: " + jobDescription;

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(candidateService.getAllCandidates());
        String string2 = "And here are the candidates: " + jsonString;

        String string3 = "Add a rank property inside of each candidate object. " +
                "Append the rank of each candidate by a value calculated using the following criteria: ";

        String prompt = string1 + "\n" + string2 + "\n" + string3 + CANDIDATE_RANKING_OPTIONS;

        GPTResponse response = gptService.generateResponse(prompt);
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            throw new Exception("No response from AI");
        }
        return response.getChoices().get(0).getMessage().getContent();
    }
}

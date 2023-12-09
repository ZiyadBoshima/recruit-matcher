package com.ziyad.recruitingspring.service;

import com.ziyad.recruitingspring.model.Candidate;
import com.ziyad.recruitingspring.repository.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class JobServiceTest {

    @InjectMocks
    private CandidateService candidateService;
    @Mock
    private CandidateRepository candidateRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void rankCandidates_validOutput_true() {
        String jobDescription = "job description";
        String string1 = "Here is the job description: " + jobDescription;

        List<Candidate> candidates = candidateService.getAllCandidates();
        String string2 = "And here are the candidates: " + candidates;
        System.out.println(string2);
    }
}

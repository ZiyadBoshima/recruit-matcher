package com.ziyad.recruitingspring.constants;

public class Constants {

    public static final String CANDIDATE_RANKING_OPTIONS = "Starting the rank from 0, for ever candidate skill that matches a requirement in the job" +
            " description, add 1. If the job is that of a senior position, consider the years of experience, is it above 5? If so, add 2. Return everything in json format.";
    public static final String RESUME_PROCESSING_OPTIONS = "the name, skills (a json array), and years of experience(labeled yearsOfExperience, " +
            "which represents professional experience only and is indicated by one integer value. Years of experience is not an array).";
    public enum JobType {
        FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP
    }

    public enum Remote {
        ONSITE, REMOTE, HYBRID
    }
}

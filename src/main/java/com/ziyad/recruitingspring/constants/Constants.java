package com.ziyad.recruitingspring.constants;

public class Constants {

    public static final String RESUME_PROCESSING_OPTIONS = "the name, skills (a json array), and years of experience(labeled yearsOfExperience, " +
            "which represents professional experience only and is indicated by one integer value. Years of experience is not an array).";
    public enum JobType {
        FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP
    }

    public enum Remote {
        ONSITE, REMOTE, HYBRID
    }
}

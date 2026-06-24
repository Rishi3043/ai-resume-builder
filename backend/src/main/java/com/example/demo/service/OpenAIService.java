package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    public String generateProfessionalSummary(
            String fullName,
            String role,
            String skills,
            int yearsOfExperience
    ) {

        String experienceLevel;

        if (yearsOfExperience <= 1) {
            experienceLevel = "motivated and detail-oriented fresher";
        } else if (yearsOfExperience <= 3) {
            experienceLevel = "dynamic and results-driven professional";
        } else if (yearsOfExperience <= 7) {
            experienceLevel = "experienced and performance-focused specialist";
        } else {
            experienceLevel = "highly accomplished and strategic leader";
        }

        return fullName + " is a " + experienceLevel +
                " with " + yearsOfExperience + " years of experience as a " + role +
                ". Skilled in " + skills +
                ", with a strong ability to deliver high-quality results, solve complex problems, and contribute effectively to team success.";
    }
}
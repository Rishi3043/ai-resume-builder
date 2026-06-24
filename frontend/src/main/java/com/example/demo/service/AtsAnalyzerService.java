package com.example.demo.service;

import com.example.demo.entity.Resume;
import com.example.demo.dto.AtsAnalysisResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AtsAnalyzerService {

    public AtsAnalysisResponse analyzeResume(Resume resume) {

        int score = 0;

        List<String> strengths = new ArrayList<>();
        List<String> improvements = new ArrayList<>();
        List<String> missingKeywords = new ArrayList<>();


        // ===== BASIC INFO CHECK =====

        if (resume.getFullName() != null && !resume.getFullName().isEmpty()) {
            score += 5;
        } else {
            improvements.add("Add your full name");
        }

        if (resume.getTitle() != null && !resume.getTitle().isEmpty()) {
            score += 5;
        } else {
            improvements.add("Add a professional title");
        }

        if (resume.getSummary() != null && !resume.getSummary().isEmpty()) {
            score += 10;
            strengths.add("Professional summary present");
        } else {
            improvements.add("Add a professional summary");
        }


        // ===== SKILLS ANALYSIS =====

        if (resume.getSkills() != null) {

            String[] skills = resume.getSkills().split(",");

            if (skills.length >= 5) {
                score += 20;
                strengths.add("Strong skill section");
            }
            else if (skills.length >= 3) {
                score += 15;
                improvements.add("Add more technical skills");
            }
            else {
                score += 5;
                improvements.add("Skills section is too small");
            }
        }


        // ===== LINKS CHECK =====

        if (resume.getGithub() != null && !resume.getGithub().isEmpty()) {
            score += 5;
            strengths.add("GitHub profile included");
        } else {
            improvements.add("Add GitHub profile");
        }

        if (resume.getLinkedin() != null && !resume.getLinkedin().isEmpty()) {
            score += 5;
            strengths.add("LinkedIn profile included");
        } else {
            improvements.add("Add LinkedIn profile");
        }


        // ===== EXPERIENCE CHECK =====

        if (resume.getExperiences() != null && !resume.getExperiences().isEmpty()) {

            score += 20;
            strengths.add("Work experience added");

        } else {

            improvements.add("Add work experience section");

        }


        // ===== KEYWORD ANALYSIS =====

        List<String> importantKeywords = Arrays.asList(
                "java",
                "spring boot",
                "rest api",
                "sql",
                "microservices",
                "docker"
        );

        if (resume.getSkills() != null) {

            String skillText = resume.getSkills().toLowerCase();

            for (String keyword : importantKeywords) {

                if (!skillText.contains(keyword)) {

                    missingKeywords.add(keyword);

                }
            }
        }


        if (score > 100) {
            score = 100;
        }

        return new AtsAnalysisResponse(score, strengths, improvements, missingKeywords);
    }
}
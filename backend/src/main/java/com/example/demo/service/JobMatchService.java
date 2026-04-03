package com.example.demo.service;

import com.example.demo.dto.JobMatchResponse;
import com.example.demo.entity.Resume;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JobMatchService {

    public JobMatchResponse analyzeJobMatch(Resume resume, String jobDescription) {

        String resumeSkills = resume.getSkills() == null ? "" : resume.getSkills().toLowerCase();
        String jobDesc = jobDescription == null ? "" : jobDescription.toLowerCase();

        List<String> matchedSkills = new ArrayList<>();
        List<String> missingSkills = new ArrayList<>();

        List<String> commonSkills = Arrays.asList(
                "java",
                "spring",
                "spring boot",
                "react",
                "mysql",
                "docker",
                "aws"
        );

        for (String skill : commonSkills) {

            if (resumeSkills.contains(skill) && jobDesc.contains(skill)) {
                matchedSkills.add(skill);
            }

            else if (jobDesc.contains(skill)) {
                missingSkills.add(skill);
            }
        }

        int total = matchedSkills.size() + missingSkills.size();
        int score = total == 0 ? 0 : (matchedSkills.size() * 100) / total;

        JobMatchResponse response = new JobMatchResponse();
        response.setMatchScore(score);
        response.setMatchedSkills(matchedSkills);
        response.setMissingSkills(missingSkills);

        return response;
    }
}
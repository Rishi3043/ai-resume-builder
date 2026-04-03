package com.example.demo.controller;

import com.example.demo.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AIController {

    @Autowired
    private OpenAIService openAIService;

    @RequestMapping(value = "/generate-summary", method = {RequestMethod.GET, RequestMethod.POST})
    public String generateSummary(
            @RequestParam("fullName") String fullName,
            @RequestParam("role") String role,
            @RequestParam("skills") String skills,
            @RequestParam("yearsOfExperience") Integer yearsOfExperience
    ) {

        return openAIService.generateProfessionalSummary(
                fullName,
                role,
                skills,
                yearsOfExperience
        );
    }
}
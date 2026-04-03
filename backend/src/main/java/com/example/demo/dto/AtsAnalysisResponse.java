package com.example.demo.dto;

import java.util.List;

public class AtsAnalysisResponse {

    private int score;
    private List<String> strengths;
    private List<String> improvements;
    private List<String> missingKeywords;

    // ✅ Empty constructor
    public AtsAnalysisResponse() {
    }

    // ✅ Constructor used by AtsAnalyzerService
    public AtsAnalysisResponse(int score,
                               List<String> strengths,
                               List<String> improvements,
                               List<String> missingKeywords) {
        this.score = score;
        this.strengths = strengths;
        this.improvements = improvements;
        this.missingKeywords = missingKeywords;
    }

    // ✅ Getters and Setters

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getStrengths() {
        return strengths;
    }

    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }

    public List<String> getImprovements() {
        return improvements;
    }

    public void setImprovements(List<String> improvements) {
        this.improvements = improvements;
    }

    public List<String> getMissingKeywords() {
        return missingKeywords;
    }

    public void setMissingKeywords(List<String> missingKeywords) {
        this.missingKeywords = missingKeywords;
    }
}
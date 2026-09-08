package com.aicodereview.platform.analysis;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoringService {

    public int calculateScore(List<AnalysisIssue> issues) {
        int score = 100;
        for (AnalysisIssue issue : issues) {
            switch (issue.getSeverity()) {
                case "HIGH" -> score -= 15;
                case "MEDIUM" -> score -= 7;
                case "LOW" -> score -= 3;
                default -> score -= 3;
            }
        }
        return Math.max(score, 0);
    }
}
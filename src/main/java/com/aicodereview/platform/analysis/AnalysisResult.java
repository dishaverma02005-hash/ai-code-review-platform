package com.aicodereview.platform.analysis;

import java.util.List;

public class AnalysisResult {
    private List<AnalysisIssue> issues;
    private int totalIssues;
    private int score;

    public AnalysisResult(List<AnalysisIssue> issues, int totalIssues, int score) {
        this.issues = issues;
        this.totalIssues = totalIssues;
        this.score = score;
    }

    public List<AnalysisIssue> getIssues() { return issues; }
    public int getTotalIssues() { return totalIssues; }
    public int getScore() { return score; }
}
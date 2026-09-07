package com.aicodereview.platform.analysis;

import java.util.List;

public class AnalysisResult {
    private List<AnalysisIssue> issues;
    private int totalIssues;

    public AnalysisResult(List<AnalysisIssue> issues, int totalIssues) {
        this.issues = issues;
        this.totalIssues = totalIssues;
    }

    public List<AnalysisIssue> getIssues() { return issues; }
    public int getTotalIssues() { return totalIssues; }
}
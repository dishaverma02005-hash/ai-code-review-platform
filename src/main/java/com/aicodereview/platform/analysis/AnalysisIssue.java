package com.aicodereview.platform.analysis;

public class AnalysisIssue {
    private String type;
    private String message;
    private int line;

    public AnalysisIssue(String type, String message, int line) {
        this.type = type;
        this.message = message;
        this.line = line;
    }

    public String getType() { return type; }
    public String getMessage() { return message; }
    public int getLine() { return line; }
}
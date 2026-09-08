package com.aicodereview.platform.analysis;

public class AnalysisIssue {
    private String type;
    private String message;
    private int line;
    private String severity;

    public AnalysisIssue(String type, String message, int line, String severity) {
        this.type = type;
        this.message = message;
        this.line = line;
        this.severity = severity;
    }

    public String getType() { return type; }
    public String getMessage() { return message; }
    public int getLine() { return line; }
    public String getSeverity() { return severity; }
}
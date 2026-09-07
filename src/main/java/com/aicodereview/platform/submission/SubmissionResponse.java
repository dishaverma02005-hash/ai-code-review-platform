package com.aicodereview.platform.submission;

import java.time.Instant;

public class SubmissionResponse {

    private Long id;
    private String code;
    private Instant submittedAt;
    private String username;

    public SubmissionResponse() {}

    public SubmissionResponse(Long id, String code, Instant submittedAt, String username) {
        this.id = id;
        this.code = code;
        this.submittedAt = submittedAt;
        this.username = username;
    }

    /** Maps a Submission entity to a SubmissionResponse DTO. */
    public static SubmissionResponse from(Submission s) {
        return new SubmissionResponse(
            s.getId(),
            s.getCode(),
            s.getSubmittedAt(),
            s.getUser().getUsername()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Instant submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

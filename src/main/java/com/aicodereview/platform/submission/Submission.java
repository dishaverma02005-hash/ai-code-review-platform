package com.aicodereview.platform.submission;

import com.aicodereview.platform.user.User;
import jakarta.persistence.*;
import java.time.Instant;

/**
 * Submission entity — represents a row in the 'submissions' table.
 *
 * Created on Day 4 of the AI Code Review Platform project.
 * Fields:
 *  - id: auto-generated primary key
 *  - code: the Java source code submitted by the user (TEXT, up to 50 KB)
 *  - submittedAt: timestamp when the row was inserted
 *  - user: the owning User (LAZY ManyToOne, user_id FK)
 *
 * TODO (Day 12 cleanup): consider ON DELETE CASCADE at the DB level.
 * Hibernate's @ManyToOne does not emit ON DELETE CASCADE in the DDL,
 * so deleting a user will currently fail with an FK violation if/when
 * we add a delete-user endpoint. For Day 4 we have no delete flow.
 */
@Entity
@Table(
    name = "submissions",
    indexes = {
        @Index(name = "idx_submissions_user_id", columnList = "user_id")
    }
)
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, columnDefinition = "TEXT")
    private String code;

    @Column(name = "submitted_at", nullable = false, updatable = false)
    private Instant submittedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Called by JPA just before the row is inserted the first time. */
    @PrePersist
    void onCreate() {
        this.submittedAt = Instant.now();
    }

    // --- Getters and setters ---

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

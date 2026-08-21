package com.aicodereview.platform.user;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * User entity — represents a row in the 'users' table.
 *
 * Created on Day 2 of the AI Code Review Platform project.
 * Fields:
 *  - id: auto-generated primary key
 *  - username: unique handle, also used for login
 *  - email: unique email address
 *  - passwordHash: BCrypt-hashed password (plaintext is NEVER stored)
 *  - createdAt: timestamp when the row was inserted
 */
@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_username", columnNames = "username"),
        @UniqueConstraint(name = "uk_users_email", columnNames = "email")
    }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /** Called by JPA just before the row is inserted the first time. */
    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    // --- Getters and setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

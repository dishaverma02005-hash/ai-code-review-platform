package com.aicodereview.platform.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for the User entity.
 *
 * By extending JpaRepository, Spring Data JPA gives us, for free:
 *   - save(User)
 *   - findById(Long)
 *   - findAll()
 *   - deleteById(Long)
 *   - count()
 *   - and many more...
 *
 * We just add custom query methods below.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /** Used during login (Day 3) to look up a user by their username. */
    Optional<User> findByUsername(String username);

    /** Used during registration to check if a username is already taken. */
    boolean existsByUsername(String username);

    /** Used during registration to check if an email is already registered. */
    boolean existsByEmail(String email);
}

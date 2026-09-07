package com.aicodereview.platform.submission;

import com.aicodereview.platform.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for the Submission entity.
 *
 * By extending JpaRepository, Spring Data JPA gives us, for free:
 *   - save(Submission)
 *   - findById(Long)
 *   - findAll()
 *   - deleteById(Long)
 *   - count()
 *   - and many more...
 *
 * We just add custom query methods below.
 */
@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    /** Returns all submissions belonging to the given user, newest first. */
    List<Submission> findAllByUserOrderBySubmittedAtDesc(User user);

    /** Returns a submission only if it belongs to the given user. Used for safe single-row lookups. */
    Optional<Submission> findByIdAndUser(Long id, User user);
}

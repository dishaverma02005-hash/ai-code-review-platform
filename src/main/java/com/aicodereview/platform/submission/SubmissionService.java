package com.aicodereview.platform.submission;

import com.aicodereview.platform.user.User;
import com.aicodereview.platform.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;

    public SubmissionService(SubmissionRepository submissionRepository,
                             UserRepository userRepository) {
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public SubmissionResponse save(CreateSubmissionRequest request) {
        // 1. Identify the currently logged-in user (set by JwtAuthFilter)
        User user = currentUser();

        // 2. Build a new Submission, link it to the user, persist it
        Submission submission = new Submission();
        submission.setCode(request.getCode());
        submission.setUser(user);

        Submission saved = submissionRepository.save(submission);

        // 3. Map entity -> DTO and return
        return SubmissionResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<SubmissionResponse> listForCurrentUser() {
        User user = currentUser();
        return submissionRepository
                .findAllByUserOrderBySubmittedAtDesc(user)
                .stream()
                .map(SubmissionResponse::from)
                .toList();
    }

    /** Pulls the username out of the Spring Security context and loads the User. */
    private User currentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthenticated");
        }
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}

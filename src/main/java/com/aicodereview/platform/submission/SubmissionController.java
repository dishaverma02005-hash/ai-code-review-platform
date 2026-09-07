package com.aicodereview.platform.submission;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * SubmissionController - code submission endpoints.
 *
 * POST /api/submissions  -> submit Java code, returns 201 with the saved submission
 * GET  /api/submissions  -> list the current user's submissions, newest first
 *
 * All endpoints require a valid JWT (anyRequest().authenticated() in SecurityConfig).
 */
@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    /**
     * Create a new submission for the authenticated user.
     * Body: { "code": "public class Hello { }" }
     * Returns 201 Created with a Location header pointing to the new resource.
     */
    @PostMapping
    public ResponseEntity<SubmissionResponse> create(
            @Valid @RequestBody CreateSubmissionRequest request) {

        SubmissionResponse body = submissionService.save(request);
        return ResponseEntity
                .created(URI.create("/api/submissions/" + body.getId()))
                .body(body);
    }

    /**
     * List all submissions belonging to the authenticated user, newest first.
     * Returns 200 OK with a JSON array (possibly empty).
     */
    @GetMapping
    public List<SubmissionResponse> list() {
        return submissionService.listForCurrentUser();
    }
}

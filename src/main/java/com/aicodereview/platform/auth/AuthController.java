package com.aicodereview.platform.auth;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController - the public URLs for sign-up and sign-in.
 *
 * POST /api/auth/register -> create a new user, return JWT
 * POST /api/auth/login    -> check credentials, return JWT
 *
 * Both endpoints accept JSON and return JSON.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Register a new user.
     * Body: { "username": "disha", "email": "disha@example.com", "password": "secret123" }
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Log in an existing user.
     * Body: { "username": "disha", "password": "secret123" }
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
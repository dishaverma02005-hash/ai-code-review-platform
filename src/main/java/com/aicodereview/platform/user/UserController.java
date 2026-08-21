package com.aicodereview.platform.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * UserController — exposes user-related REST endpoints.
 *
 * Day 2: only GET /api/users (list all users from DB).
 * Day 3 will add POST /api/users (registration) and POST /api/users/login.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * GET /api/users
     * Returns every user in the database as JSON.
     * Right now this will return [] because we haven't added signup yet.
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

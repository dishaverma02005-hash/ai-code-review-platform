package com.aicodereview.platform.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthFilter - runs on every request.
 * If the request has "Authorization: Bearer <token>", it verifies the token
 * and tells Spring who the user is.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Look for "Authorization: Bearer <token>" header
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Strip "Bearer " to get the raw token
        String token = authHeader.substring(7);

        // 3. Try to read the username from the token
        try {
            String username = jwtService.extractUsername(token);

            // 4. Check username and existing authentication
            if (username != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {

                // 5. Look up the user in the database
                var userDetails = userDetailsService.loadUserByUsername(username);

                // 6. Build an authenticated object Spring understands
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 7. Store authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (Exception e) {
            // Invalid or expired token.
            // Leave the user unauthenticated.
        }

        // 8. Continue to the next filter/controller
        filterChain.doFilter(request, response);
    }
}
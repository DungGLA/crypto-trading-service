package com.assignment.cryptotradingservice.common.helper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserContext userContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                Long userId = parseUserIdFromToken(token);

                userContext.setUserId(userId);
            } else {
                userContext.setUserId(1L); // for testing when call api without token, set userId to 1L
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            // TODO: handle exception, maybe return 401 Unauthorized
        }
    }

    private Long parseUserIdFromToken(String token) {
        // TODO: decode JWT and extract userId
        return 1L; // return current user id data in database for testing
    }
}

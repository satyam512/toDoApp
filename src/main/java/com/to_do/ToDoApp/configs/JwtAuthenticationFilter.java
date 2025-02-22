package com.to_do.ToDoApp.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtil;
    private final UserContext userContext;

    public JwtAuthenticationFilter(JwtUtils jwtUtil, UserContext userContext) {
        this.jwtUtil = jwtUtil;
        this.userContext = userContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                Integer userId = jwtUtil.getUserIdFromToken(token);
                userContext.setUserId(userId); // Store userId in ThreadLocal
            }

            filterChain.doFilter(request, response);

        } finally {
            userContext.clear(); // Clean up ThreadLocal to prevent memory leaks
        }
    }
}

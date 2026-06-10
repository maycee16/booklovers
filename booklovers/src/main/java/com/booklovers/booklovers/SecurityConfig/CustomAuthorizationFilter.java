package com.booklovers.booklovers.SecurityConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.booklovers.booklovers.Utility.JwtUtil;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.nimbusds.jwt.JWTClaimsSet;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public CustomAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Define paths that do not require authentication
        ArrayList<String> pathsAllowed = new ArrayList<>();
        pathsAllowed.add("/api/open/");
                pathsAllowed.add("/api/auth/");

       


        // pathsAllowed.add("/api/");

        if (checkPathsAllowed(pathsAllowed, request.getServletPath()) ||
                request.getServletPath().contains("docs") ||
                request.getServletPath().contains("/swagger-ui")) {
            log.info("Allowed path {} to go through", request.getServletPath());
            filterChain.doFilter(request, response);
            return;
        }

        // Extract Authorization header
        String authToken = request.getHeader("Authorization");
        log.info("Received Authorization token: {}", authToken);

        if (authToken == null) {
            log.warn("No token provided");
            exitPlan(response, "No token provided");
            return;
        }

        if (!authToken.startsWith("Bearer ")) {
            log.warn("Token must be a Bearer token");
            exitPlan(response, "Invalid token format. Use 'Bearer <token>'");
            return;
        }

        // Remove "Bearer " prefix
        authToken = authToken.substring("Bearer ".length());
        log.debug("Extracted JWT: {}", authToken);

        try {
            // Validate and extract claims
            JWTClaimsSet claims = jwtUtil.validateAndGetClaims(authToken);
            String email = claims.getSubject();
            String username = claims.getStringClaim("username");

            log.info("Authenticated Email: {}, Username: {}", email, username);

            // Set authentication in SecurityContext
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("JWT processing error: {}", e.getMessage());
            exitPlan(response, "Invalid or expired token: " + e.getMessage());
        }
    }

    private boolean checkPathsAllowed(ArrayList<String> pathsAllowed, String servletPath) {
        return pathsAllowed.stream().anyMatch(servletPath::startsWith);
    }

    private void exitPlan(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("status", 401);
        resMap.put("message", message);

        new JsonMapper().writeValue(response.getOutputStream(), resMap);
    }
}

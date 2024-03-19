package com.deepakallcode.codesnippetmanager.config;

import com.deepakallcode.codesnippetmanager.services.AuthService;
import com.deepakallcode.codesnippetmanager.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private static final Logger logger = Logger.getLogger(JwtFilter.class.getName());

    JwtFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader;
        String jwt;
        authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            jwt = authHeader.substring(7);
            String email = this.jwtService.getEmailFromToken(jwt);
            if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
                if (this.jwtService.verifyToken(jwt)) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                            (userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}

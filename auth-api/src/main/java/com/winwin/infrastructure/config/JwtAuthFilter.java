package com.winwin.infrastructure.config;

import com.winwin.domain.port.CustomUserDetailsService;
import com.winwin.domain.port.JwtTokenSpi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenSpi tokenSpi;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtTokenSpi tokenSpi, CustomUserDetailsService userDetailsService) {
        this.tokenSpi = tokenSpi;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractBearerToken(request);
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            tokenSpi.verifyToken(token).ifPresent(verified -> {
                UserDetails user = userDetailsService.loadUserById(verified.subject());
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            });
        }
        filterChain.doFilter(request, response);
    }

    private String extractBearerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")) return null;
        return header.substring(7);
    }
}

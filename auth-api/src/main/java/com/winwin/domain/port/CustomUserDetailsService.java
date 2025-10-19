package com.winwin.domain.port;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface CustomUserDetailsService extends UserDetailsService {
    UserDetails loadUserById(UUID userId);
}

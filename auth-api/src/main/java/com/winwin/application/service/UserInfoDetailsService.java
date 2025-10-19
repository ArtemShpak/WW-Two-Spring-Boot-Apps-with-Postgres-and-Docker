package com.winwin.application.service;

import com.winwin.domain.port.CustomUserDetailsService;
import com.winwin.domain.service.UserService;
import com.winwin.infrastructure.adapter.inbound.security.UserInfoDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserInfoDetailsService implements CustomUserDetailsService {

    private final UserService userService;

    public UserInfoDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userService.findByEmail(username)
                .map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    public UserDetails loadUserById(UUID userId) {
        return userService.findById(userId)
                .map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
    }

    public boolean userExists(String email) {
        return userService.findByEmail(email).isPresent();
    }
}

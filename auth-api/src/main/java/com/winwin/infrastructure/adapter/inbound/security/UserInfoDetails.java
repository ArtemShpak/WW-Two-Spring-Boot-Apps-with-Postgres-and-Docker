package com.winwin.infrastructure.adapter.inbound.security;

import com.winwin.domain.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserInfoDetails implements UserDetails {
    private final String username;
    private final String password;

    // Constructor used by the service when mapping from domain User
    public UserInfoDetails(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
    }

    // Keep a simple constructor for tests or other adapters
    public UserInfoDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

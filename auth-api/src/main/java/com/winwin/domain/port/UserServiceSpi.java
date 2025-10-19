package com.winwin.domain.port;

import com.winwin.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserServiceSpi {
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
    boolean existsByEmail(String email);
    void saveUser(User user);
}

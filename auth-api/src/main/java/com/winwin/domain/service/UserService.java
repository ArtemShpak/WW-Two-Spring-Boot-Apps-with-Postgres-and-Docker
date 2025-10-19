package com.winwin.domain.service;

import com.winwin.domain.model.User;
import com.winwin.domain.port.UserServiceSpi;
import com.winwin.infrastructure.adapter.outbound.persistence.JpaUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserServiceSpi {

    private final JpaUserRepository jpaUserRepository;

    public UserService(JpaUserRepository userRepository) {
        this.jpaUserRepository = userRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.findByEmail(email).isPresent();
    }

    @Override
    public void saveUser(User user) {
        jpaUserRepository.save(user);
    }
}

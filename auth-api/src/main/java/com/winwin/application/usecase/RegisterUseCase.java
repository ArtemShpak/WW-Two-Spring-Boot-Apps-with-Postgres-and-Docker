package com.winwin.application.usecase;

import com.winwin.domain.model.User;
import com.winwin.domain.port.JwtTokenSpi;
import com.winwin.domain.port.RegisterSpi;
import com.winwin.domain.service.UserService;
import com.winwin.infrastructure.adapter.inbound.rest.dto.AuthRequest;
import com.winwin.infrastructure.adapter.inbound.rest.dto.AuthResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterUseCase implements RegisterSpi {

    private final UserService userService;
    private final JwtTokenSpi jwtTokenSpi;
    private final PasswordEncoder passwordEncoder;

    public RegisterUseCase(UserService userService, JwtTokenSpi jwtTokenSpi, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenSpi = jwtTokenSpi;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse register(AuthRequest request) {
        if(userService.existsByEmail(request.email())) {
            throw new IllegalArgumentException("User already exists with email: " + request.email());
        } else {
            String hashedPassword = passwordEncoder.encode(request.password());
            User newUser = new User(request.email(), hashedPassword);
            userService.saveUser(newUser);
            String token = jwtTokenSpi.generateToken(newUser.getId());
            return new AuthResponse("token:" + token);
        }
    }

}

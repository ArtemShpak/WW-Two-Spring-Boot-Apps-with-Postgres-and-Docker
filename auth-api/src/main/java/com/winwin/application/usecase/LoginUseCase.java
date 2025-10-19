package com.winwin.application.usecase;

import com.winwin.domain.model.User;
import com.winwin.domain.port.JwtTokenSpi;
import com.winwin.domain.port.LoginSpi;
import com.winwin.domain.port.UserServiceSpi;
import com.winwin.infrastructure.adapter.inbound.rest.dto.AuthRequest;
import com.winwin.infrastructure.adapter.inbound.rest.dto.AuthResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

@Component
public class LoginUseCase implements LoginSpi {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenSpi jwtTokenSpi;
    private final UserServiceSpi userService;

    public LoginUseCase(AuthenticationManager authenticationManager, JwtTokenSpi jwtTokenSpi, UserServiceSpi userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenSpi = jwtTokenSpi;
        this.userService = userService;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            User user = userService.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("User not found"));
            String token = jwtTokenSpi.generateToken(user.getId());
            return new AuthResponse(token);
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed", e);
        }
    }

}

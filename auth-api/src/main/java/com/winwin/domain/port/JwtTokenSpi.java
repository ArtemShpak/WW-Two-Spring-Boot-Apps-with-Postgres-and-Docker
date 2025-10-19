package com.winwin.domain.port;

import com.winwin.domain.model.VerifiedToken;

import java.util.Optional;
import java.util.UUID;

public interface  JwtTokenSpi {
    String generateToken(UUID userId);
    Optional<VerifiedToken> verifyToken(String token);
    Optional<UUID> extractUserId(String token);
}

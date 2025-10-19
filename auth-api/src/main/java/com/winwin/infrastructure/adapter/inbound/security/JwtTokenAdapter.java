package com.winwin.infrastructure.adapter.inbound.security;

import com.winwin.domain.model.VerifiedToken;
import com.winwin.domain.port.JwtTokenSpi;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtTokenAdapter implements JwtTokenSpi {

    @Value("${jwt.secret}")
    private String SECRET;
    private static final long ACCESS_TTL_MILLIS = 30 * 60 * 1000;

    @Override
    public String generateToken(UUID userId) {
        try {
            Date now = new Date();
            Date exp = new Date(now.getTime() + ACCESS_TTL_MILLIS);
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(userId.toString())
                    .issueTime(now)
                    .expirationTime(exp)
                    .jwtID(UUID.randomUUID().toString())
                    .build();
            SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
            JWSSigner signer = new MACSigner(SECRET);
            jwt.sign(signer);
            return jwt.serialize();
        } catch (Exception e) {
            throw new IllegalStateException("Не вдалось створити JWT", e);
        }
    }

    @Override
    public Optional<VerifiedToken> verifyToken(String rawToken) {
        Optional<JWTClaimsSet> claims = parse(rawToken)
                .filter(this::signatureValid)
                .flatMap(this::extractClaims)
                .filter(this::notExpired);

        return Optional.of(claims.flatMap(c -> toUUID(c.getSubject())
                        .map(userId -> new VerifiedToken(userId, true)))
                .orElseGet(() -> new VerifiedToken(null, false)));
    }

    private Optional<UUID> toUUID(String value) {
        try {
            return Optional.of(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private Optional<SignedJWT> parse(String raw) {
        try {
            return Optional.of(SignedJWT.parse(raw));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private boolean signatureValid(SignedJWT jwt) {
        try {
            JWSVerifier verifier = new MACVerifier(SECRET);
            return jwt.verify(verifier);
        } catch (Exception e) {
            return false;
        }
    }

    private Optional<JWTClaimsSet> extractClaims(SignedJWT jwt) {
        try {
            return Optional.of(jwt.getJWTClaimsSet());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private boolean notExpired(JWTClaimsSet claims) {
        Date exp = claims.getExpirationTime();
        return exp != null && Instant.now().isBefore(exp.toInstant());
    }

    @Override
    public Optional<UUID> extractUserId(String token) {
        return parse(token)
                .filter(this::signatureValid)
                .flatMap(this::extractClaims)
                .filter(this::notExpired)
                .map(JWTClaimsSet::getSubject)
                .flatMap(this::toUUID);
    }
}


package com.winwin.domain.model;

import java.util.UUID;

public record VerifiedToken(UUID subject, boolean expiration) {
}

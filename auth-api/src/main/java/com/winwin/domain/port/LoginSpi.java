package com.winwin.domain.port;

import com.winwin.infrastructure.adapter.inbound.rest.dto.AuthRequest;
import com.winwin.infrastructure.adapter.inbound.rest.dto.AuthResponse;

public interface LoginSpi {
    AuthResponse login(AuthRequest request);
}

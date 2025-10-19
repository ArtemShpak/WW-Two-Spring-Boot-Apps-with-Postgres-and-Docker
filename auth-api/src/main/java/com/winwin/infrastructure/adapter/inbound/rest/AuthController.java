package com.winwin.infrastructure.adapter.inbound.rest;

import com.winwin.domain.port.LoginSpi;
import com.winwin.domain.port.RegisterSpi;
import com.winwin.infrastructure.adapter.inbound.rest.dto.AuthRequest;
import com.winwin.infrastructure.adapter.inbound.rest.dto.AuthResponse;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableFeignClients
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegisterSpi registerSpi;
    private final LoginSpi loginSpi;

    public AuthController(RegisterSpi registerSpi, LoginSpi loginSpi) {
        this.registerSpi = registerSpi;
        this.loginSpi = loginSpi;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        return registerSpi.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return loginSpi.login(request);
    }
}

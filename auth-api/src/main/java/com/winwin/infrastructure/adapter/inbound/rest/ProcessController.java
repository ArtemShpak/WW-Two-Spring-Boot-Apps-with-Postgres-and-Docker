package com.winwin.infrastructure.adapter.inbound.rest;

import com.winwin.domain.port.JwtTokenSpi;
import com.winwin.domain.service.ProcessService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/process")
public class ProcessController {

    private final ProcessService processService;
    private final JwtTokenSpi jwtTokenSpi;

    public ProcessController(ProcessService processService, JwtTokenSpi jwtTokenSpi) {
        this.processService = processService;
        this.jwtTokenSpi = jwtTokenSpi;
    }

    @PostMapping
    public Map<String, String> process(@RequestBody Map<String, String> inputData, @RequestHeader("Authorization") String authorizationHeader) {
        String text = inputData.get("text");
        String token = authorizationHeader.substring(7);
        UUID userId = jwtTokenSpi.extractUserId(token).orElse(null);
        String result = processService.processData(userId, text);
        return Map.of("result", result);
    }
}

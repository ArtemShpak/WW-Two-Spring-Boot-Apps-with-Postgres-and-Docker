package com.winwin.infrastructure.adapter.inbound.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class DataController {

    @Value("${internal.secret.token}")
    private String secretKey;

    @PostMapping("/transform")
    public ResponseEntity<?> transformData(
            @RequestHeader("X-Internal-Token") String internalToken,
            @RequestBody Map<String, String> data
            ) {
        if (internalToken == null || !internalToken.equals(secretKey)) {
            return ResponseEntity.status(403).body("Forbidden: Invalid internal token");
        }

        String text = data.get("text");
        String result = new StringBuilder(text).reverse().toString().toUpperCase();

        return ResponseEntity.ok(Map.of("result", result));
    }
}

package com.winwin.infrastructure.adapter.outbound.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "data-api", url = "http://localhost:8081")
public interface DataApiClient {

    @PostMapping(value = "/api/v1/transform", consumes = "application/json")
    Map<String, String> transform(
            @RequestBody Map<String, String> body,
            @RequestHeader("X-Internal-Token") String token
    );
}

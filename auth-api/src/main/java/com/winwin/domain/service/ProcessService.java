package com.winwin.domain.service;

import com.winwin.domain.port.DataServiceSpi;
import com.winwin.infrastructure.adapter.outbound.feign.DataApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
public class ProcessService {

    @Value("${INTERNAL_SECRET_TOKEN}")
    private String secretToken;

    private final DataApiClient dataApiClient;
    private final DataServiceSpi dataServiceSpi;

    public ProcessService(DataApiClient dataApiClient, DataServiceSpi dataServiceSpi) {
        this.dataApiClient = dataApiClient;
        this.dataServiceSpi = dataServiceSpi;
    }

    public String processData(UUID userId, String inputData) {
        Map<String, String> response = dataApiClient.transform(Map.of("text", inputData), secretToken);
        String resultData = response.get("result");
        Instant now = Instant.now();
        dataServiceSpi.saveData(userId, inputData, resultData, now);
        return resultData;
    }
}

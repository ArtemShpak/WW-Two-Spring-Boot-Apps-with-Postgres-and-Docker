package com.winwin.domain.port;

import java.time.Instant;
import java.util.UUID;

public interface DataServiceSpi {
    void saveData(UUID id, String input, String output, Instant createdAt);
}

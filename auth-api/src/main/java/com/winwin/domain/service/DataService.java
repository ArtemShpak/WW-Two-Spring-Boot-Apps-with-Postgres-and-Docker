package com.winwin.domain.service;

import com.winwin.domain.model.User;
import com.winwin.domain.model.ProcessingLog;
import com.winwin.domain.port.DataServiceSpi;
import com.winwin.domain.port.UserServiceSpi;
import com.winwin.infrastructure.adapter.outbound.persistence.JpaDataRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class DataService implements DataServiceSpi {

    private final UserServiceSpi userService;
    private final JpaDataRepository jpaDataRepository;

    public DataService(UserServiceSpi userService, JpaDataRepository jpaDataRepository) {
        this.userService = userService;
        this.jpaDataRepository = jpaDataRepository;
    }

    @Override
    public void saveData(UUID id, String input, String output, Instant createdAt) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        ProcessingLog processingLog = ProcessingLog.builder()
                .user(user)
                .inputData(input)
                .outputData(output)
                .createdAt(createdAt)
                .build();
        jpaDataRepository.save(processingLog);
    }
}

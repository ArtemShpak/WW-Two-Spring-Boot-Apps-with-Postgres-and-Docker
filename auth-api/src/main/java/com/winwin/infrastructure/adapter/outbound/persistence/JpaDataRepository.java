package com.winwin.infrastructure.adapter.outbound.persistence;

import com.winwin.domain.model.ProcessingLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDataRepository extends JpaRepository<ProcessingLog, Long> {
}

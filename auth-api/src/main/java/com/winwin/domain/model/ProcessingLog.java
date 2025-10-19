package com.winwin.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "data")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProcessingLog {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "input")
    private String inputData;

    @Column(name = "output")
    private String outputData;

    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

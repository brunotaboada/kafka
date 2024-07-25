package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static jakarta.persistence.GenerationType.*;

@Entity
@Data
@Builder
public class Fee {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String code;
    private BigDecimal fee;
    private String status;
    @Builder.Default
    private Timestamp insertTime = Timestamp.from(Instant.now());
    private Timestamp updatedTime;
}
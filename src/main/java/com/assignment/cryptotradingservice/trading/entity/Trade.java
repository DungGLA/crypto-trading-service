package com.assignment.cryptotradingservice.trading.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String symbol;

    private String tradeType;

    private BigDecimal quantity;

    private BigDecimal price;

    private BigDecimal total;

    private Instant tradeTime;
}

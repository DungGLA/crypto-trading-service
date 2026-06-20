package com.assignment.cryptotradingservice.market.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Builder
public class MarketPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private BigDecimal bestBid;

    private BigDecimal bestAsk;

    private Timestamp createdAt;
}

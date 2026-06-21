package com.assignment.cryptotradingservice.market.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class PriceResponse {
    private String symbol;
    private BigDecimal bestBid;
    private BigDecimal bestAsk;
    private Instant timestamp;
}

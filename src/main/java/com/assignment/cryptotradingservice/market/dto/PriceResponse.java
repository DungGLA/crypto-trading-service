package com.assignment.cryptotradingservice.market.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class PriceResponse {
    private String symbol;
    private BigDecimal bestBid;
    private BigDecimal bestAsk;
    private Timestamp createdAt;
}

package com.assignment.cryptotradingservice.trading.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class TradeResponse {
    private String symbol;
    private String tradeType;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal total;
    private Instant tradeTime;
}

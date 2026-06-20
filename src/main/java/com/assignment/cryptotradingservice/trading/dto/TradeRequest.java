package com.assignment.cryptotradingservice.trading.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeRequest {
    private Long userId;

    private String symbol;

    private String tradeType;

    private BigDecimal quantity;
}

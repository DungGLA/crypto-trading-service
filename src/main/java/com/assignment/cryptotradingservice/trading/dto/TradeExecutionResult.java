package com.assignment.cryptotradingservice.trading.dto;

import com.assignment.cryptotradingservice.trading.entity.Wallet;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TradeExecutionResult {
    private BigDecimal price;
    private BigDecimal total;

    private Wallet updatedUsdt;
    private Wallet updatedCrypto;
}

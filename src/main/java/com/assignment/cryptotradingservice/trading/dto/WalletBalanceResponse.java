package com.assignment.cryptotradingservice.trading.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletBalanceResponse {
    private String asset;

    private BigDecimal balance;
}

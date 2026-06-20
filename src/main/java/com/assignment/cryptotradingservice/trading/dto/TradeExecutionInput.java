package com.assignment.cryptotradingservice.trading.dto;

import com.assignment.cryptotradingservice.trading.entity.Wallet;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TradeExecutionInput {
    private Long userId;
    private String symbol;
    private String tradeType;
    private BigDecimal quantity;

    private BigDecimal bestBid;
    private BigDecimal bestAsk;

    private Wallet usdtWallet;
    private Wallet cryptoWallet;

}

package com.assignment.cryptotradingservice.trading.strategy;

import com.assignment.cryptotradingservice.common.exception.InsufficientBalanceException;
import com.assignment.cryptotradingservice.trading.dto.TradeExecutionInput;
import com.assignment.cryptotradingservice.trading.dto.TradeExecutionResult;
import com.assignment.cryptotradingservice.trading.entity.Wallet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BuyTradeStrategy implements TradeExecutionStrategy {
    @Override
    public String getTradeType() {
        return "BUY";
    }

    @Override
    public TradeExecutionResult execute(TradeExecutionInput input) {
        BigDecimal price = input.getBestAsk();
        BigDecimal total = price.multiply(input.getQuantity());

        Wallet usdt = input.getUsdtWallet();
        Wallet crypto = input.getCryptoWallet();

        if (usdt.getBalance().compareTo(total) < 0) {
            throw new InsufficientBalanceException("Insufficient USDT to buy");
        }

        usdt.setBalance(usdt.getBalance().subtract(total));
        crypto.setBalance(crypto.getBalance().add(input.getQuantity()));

        return TradeExecutionResult.builder()
                .price(price)
                .total(total)
                .updatedUsdt(usdt)
                .updatedCrypto(crypto)
                .build();
    }
}

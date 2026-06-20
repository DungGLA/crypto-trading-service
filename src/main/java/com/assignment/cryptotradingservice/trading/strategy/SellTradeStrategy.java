package com.assignment.cryptotradingservice.trading.strategy;

import com.assignment.cryptotradingservice.common.exception.InsufficientBalanceException;
import com.assignment.cryptotradingservice.trading.dto.TradeExecutionInput;
import com.assignment.cryptotradingservice.trading.dto.TradeExecutionResult;
import com.assignment.cryptotradingservice.trading.entity.Wallet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("SELL")
public class SellTradeStrategy implements TradeExecutionStrategy {
    @Override
    public TradeExecutionResult execute(TradeExecutionInput input) {
        BigDecimal price = input.getBestBid();
        BigDecimal total = price.multiply(input.getQuantity());

        Wallet usdt = input.getUsdtWallet();
        Wallet crypto = input.getCryptoWallet();

        if (crypto.getBalance().compareTo(input.getQuantity()) < 0) {
            throw new InsufficientBalanceException("Insufficient asset to sell");
        }

        crypto.setBalance(crypto.getBalance().subtract(input.getQuantity()));
        usdt.setBalance(usdt.getBalance().add(total));

        return TradeExecutionResult.builder()
                .price(price)
                .total(total)
                .updatedUsdt(usdt)
                .updatedCrypto(crypto)
                .build();
    }
}

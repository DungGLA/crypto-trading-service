package com.assignment.cryptotradingservice.trading.service;

import com.assignment.cryptotradingservice.trading.dto.TradeExecutionInput;
import com.assignment.cryptotradingservice.trading.dto.TradeExecutionResult;
import com.assignment.cryptotradingservice.trading.strategy.TradeExecutionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TradeExecutionEngineImpl implements TradeExecutionEngine {
    private final Map<String, TradeExecutionStrategy> strategies;

    public TradeExecutionResult execute(TradeExecutionInput input) {
        TradeExecutionStrategy strategy = strategies.get(input.getTradeType());
        if (strategy == null) {
            throw new IllegalArgumentException(
                    "No strategy found for tradeType: " + input.getTradeType()
            );
        }

        return strategy.execute(input);
    }
}

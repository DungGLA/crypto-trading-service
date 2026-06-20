package com.assignment.cryptotradingservice.trading.service;

import com.assignment.cryptotradingservice.trading.dto.TradeExecutionInput;
import com.assignment.cryptotradingservice.trading.dto.TradeExecutionResult;
import com.assignment.cryptotradingservice.trading.strategy.TradeExecutionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TradeExecutionEngine {
    private final List<TradeExecutionStrategy> strategies;

    public TradeExecutionResult execute(TradeExecutionInput input) {

        TradeExecutionStrategy strategy = strategies.stream()
                .filter(s -> Objects.equals(s.getTradeType(), input.getTradeType()))
                .findFirst()
                .orElseThrow();

        return strategy.execute(input);
    }
}

package com.assignment.cryptotradingservice.trading.strategy;

import com.assignment.cryptotradingservice.trading.dto.TradeExecutionInput;
import com.assignment.cryptotradingservice.trading.dto.TradeExecutionResult;

public interface TradeExecutionStrategy {
    TradeExecutionResult execute(TradeExecutionInput context);
}

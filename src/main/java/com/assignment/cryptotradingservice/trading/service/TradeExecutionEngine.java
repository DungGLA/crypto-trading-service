package com.assignment.cryptotradingservice.trading.service;

import com.assignment.cryptotradingservice.trading.dto.TradeExecutionInput;
import com.assignment.cryptotradingservice.trading.dto.TradeExecutionResult;

public interface TradeExecutionEngine {
    TradeExecutionResult execute(TradeExecutionInput input);
}

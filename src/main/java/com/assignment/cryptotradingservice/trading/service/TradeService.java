package com.assignment.cryptotradingservice.trading.service;

import com.assignment.cryptotradingservice.common.response.PageResponse;
import com.assignment.cryptotradingservice.trading.dto.TradeRequest;
import com.assignment.cryptotradingservice.trading.dto.TradeResponse;
import org.springframework.data.domain.Pageable;

public interface TradeService {
    PageResponse<TradeResponse> getHistory(Pageable pageable);

    TradeResponse executeTrade(TradeRequest req);
}

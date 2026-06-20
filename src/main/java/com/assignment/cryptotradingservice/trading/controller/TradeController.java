package com.assignment.cryptotradingservice.trading.controller;

import com.assignment.cryptotradingservice.trading.dto.TradeRequest;
import com.assignment.cryptotradingservice.trading.dto.TradeResponse;
import com.assignment.cryptotradingservice.trading.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trade")
@RequiredArgsConstructor
public class TradeController {
    private final TradeService tradeService;

    @PostMapping
    public TradeResponse trade(@RequestBody TradeRequest request) {
        return tradeService.executeTrade(request);
    }
}

package com.assignment.cryptotradingservice.trading.controller;

import com.assignment.cryptotradingservice.common.response.PageResponse;
import com.assignment.cryptotradingservice.trading.dto.TradeRequest;
import com.assignment.cryptotradingservice.trading.dto.TradeResponse;
import com.assignment.cryptotradingservice.trading.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {
    private final TradeService tradeService;

    @PostMapping
    public TradeResponse trade(@RequestBody TradeRequest request) {
        return tradeService.executeTrade(request);
    }

    @GetMapping("/history")
    public PageResponse<TradeResponse> getHistory(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return tradeService.getHistory(pageable);
    }
}

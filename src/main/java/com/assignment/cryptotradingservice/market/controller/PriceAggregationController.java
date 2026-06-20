package com.assignment.cryptotradingservice.market.controller;

import com.assignment.cryptotradingservice.market.dto.PriceResponse;
import com.assignment.cryptotradingservice.market.service.PriceAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/prices")
public class PriceAggregationController {

    private final PriceAggregationService service;

    @GetMapping()
    public List<PriceResponse> getLatestPrice(@RequestParam(required = false) List<String> symbols) {
        return service.getLatestBestPrice(symbols);
    }
}

package com.assignment.cryptotradingservice.market.service;

import com.assignment.cryptotradingservice.market.dto.PriceResponse;

import java.util.List;

public interface PriceAggregationService {
    void aggregate();

    List<PriceResponse> getLatestBestPrice(List<String> symbols);
}

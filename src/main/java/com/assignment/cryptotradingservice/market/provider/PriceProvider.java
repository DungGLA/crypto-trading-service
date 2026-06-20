package com.assignment.cryptotradingservice.market.provider;

import com.assignment.cryptotradingservice.market.dto.ExchangeTicker;

import java.util.List;

public interface PriceProvider {
    String getName();

    List<ExchangeTicker> fetchPrices();
}

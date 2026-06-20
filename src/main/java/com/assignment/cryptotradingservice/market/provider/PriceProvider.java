package com.assignment.cryptotradingservice.market.provider;

import com.assignment.cryptotradingservice.market.dto.ExchangeTicker;
import com.assignment.cryptotradingservice.market.entity.MarketPrice;

import java.util.List;

public interface PriceProvider {
    String getName();

    List<ExchangeTicker> fetchPrices();
}

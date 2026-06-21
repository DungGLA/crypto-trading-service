package com.assignment.cryptotradingservice.market.converter;

import com.assignment.cryptotradingservice.market.dto.PriceResponse;
import com.assignment.cryptotradingservice.market.entity.MarketPrice;
import org.springframework.stereotype.Component;

@Component
public class PriceAggregationConverter {

    public PriceResponse toResponse(MarketPrice price) {

        return PriceResponse.builder()
                .symbol(price.getSymbol())
                .bestBid(price.getBestBid())
                .bestAsk(price.getBestAsk())
                .timestamp(price.getTimestamp())
                .build();
    }
}

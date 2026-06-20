package com.assignment.cryptotradingservice.market.provider;

import com.assignment.cryptotradingservice.market.client.HuobiClient;
import com.assignment.cryptotradingservice.market.dto.ExchangeTicker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.assignment.cryptotradingservice.common.constant.TradingConstants.SUPPORTED_PAIRS;

@Component
@RequiredArgsConstructor
public class HuobiPriceProvider implements PriceProvider {
    private final HuobiClient client;

    @Override
    public String getName() {
        return "HUOBI";
    }

    @Override
    public List<ExchangeTicker> fetchPrices() {
        return client.getPrices().stream()
                .filter(p -> SUPPORTED_PAIRS.contains(p.getSymbol().toUpperCase()))
                .map(h -> ExchangeTicker.builder()
                        .symbol(h.getSymbol().toUpperCase())
                        .bid(h.getBid())
                        .ask(h.getAsk())
                        .exchange(getName())
                        .build()
                )
                .toList();
    }
}

package com.assignment.cryptotradingservice.market.provider;

import com.assignment.cryptotradingservice.market.client.BinanceClient;
import com.assignment.cryptotradingservice.market.dto.ExchangeTicker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.assignment.cryptotradingservice.common.constant.TradingConstants.SUPPORTED_PAIRS;

@Component
@RequiredArgsConstructor
public class BinancePriceProvider implements PriceProvider {
    private final BinanceClient client;

    @Override
    public List<ExchangeTicker> fetchPrices() {
        return client.getPrices().stream()
                .filter(p -> SUPPORTED_PAIRS.contains(p.getSymbol().toUpperCase()))
                .map(b -> ExchangeTicker.builder()
                        .symbol(b.getSymbol().toUpperCase())
                        .bid(new BigDecimal(b.getBidPrice()))
                        .ask(new BigDecimal(b.getAskPrice()))
                        .exchange("BINANCE")
                        .build()
                )
                .toList();
    }
}

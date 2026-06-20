package com.assignment.cryptotradingservice.market.service;

import com.assignment.cryptotradingservice.market.converter.PriceAggregationConverter;
import com.assignment.cryptotradingservice.market.dto.ExchangeTicker;
import com.assignment.cryptotradingservice.market.dto.PriceResponse;
import com.assignment.cryptotradingservice.market.entity.MarketPrice;
import com.assignment.cryptotradingservice.market.provider.PriceProvider;
import com.assignment.cryptotradingservice.market.repository.MarketPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceAggregationService {
    private final List<PriceProvider> providers;
    private final MarketPriceRepository repository;

    private final PriceAggregationConverter converter;

    public void aggregate() {
        List<CompletableFuture<List<ExchangeTicker>>> futures =
                providers.stream()
                        .map(p -> CompletableFuture.supplyAsync(p::fetchPrices))
                        .toList();

        List<ExchangeTicker> allTickers = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .toList();

        List<MarketPrice> bestPrices = calculateBest(allTickers);
        repository.saveAll(bestPrices);
    }

    public List<PriceResponse> getLatestBestPrice(List<String> symbols) {
        List<MarketPrice> prices = repository.findLatestPrices(symbols);
        return prices.stream()
                .map(converter::toResponse)
                .toList();
    }

    private List<MarketPrice> calculateBest(List<ExchangeTicker> tickers) {

        return tickers.stream()
                .collect(Collectors.groupingBy(ExchangeTicker::getSymbol))
                .values()
                .stream()
                .map(list -> {

                    BigDecimal bestBid = list.stream()
                            .map(ExchangeTicker::getBid)
                            .max(BigDecimal::compareTo)
                            .orElse(BigDecimal.ZERO);

                    BigDecimal bestAsk = list.stream()
                            .map(ExchangeTicker::getAsk)
                            .min(BigDecimal::compareTo)
                            .orElse(BigDecimal.ZERO);

                    String symbol = list.get(0).getSymbol();

                    return MarketPrice.builder()
                            .symbol(symbol)
                            .bestBid(bestBid)
                            .bestAsk(bestAsk)
                            .createdAt(Timestamp.from(java.time.Instant.now()))
                            .build();
                })
                .toList();
    }

}

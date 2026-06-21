package com.assignment.cryptotradingservice.market.service;

import com.assignment.cryptotradingservice.market.converter.PriceAggregationConverter;
import com.assignment.cryptotradingservice.market.dto.ExchangeTicker;
import com.assignment.cryptotradingservice.market.dto.PriceResponse;
import com.assignment.cryptotradingservice.market.entity.MarketPrice;
import com.assignment.cryptotradingservice.market.provider.PriceProvider;
import com.assignment.cryptotradingservice.market.repository.MarketPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceAggregationServiceImpl implements PriceAggregationService {
    private final List<PriceProvider> providers;
    private final MarketPriceRepository repository;

    private final PriceAggregationConverter converter;

    @Override
    public void aggregate() {
        List<CompletableFuture<List<ExchangeTicker>>> futures =
                providers.stream()
                        .map(p -> CompletableFuture.supplyAsync(p::fetchPrices).exceptionally(ex -> {
                            log.error("Error fetching prices from provider: " + ex.getMessage() );
                            return List.of();
                        }))
                        .toList();

        List<ExchangeTicker> allTickers = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .toList();

        List<MarketPrice> bestPrices = calculateBestPrice(allTickers);
        repository.saveAll(bestPrices);
    }

    @Override
    public List<PriceResponse> getLatestBestPrice(List<String> symbols) {
        boolean isGetBySymbol = !CollectionUtils.isEmpty(symbols);
        List<MarketPrice> prices = repository.findLatestPrices(symbols, isGetBySymbol);
        return prices.stream()
                .map(converter::toResponse)
                .toList();
    }

    private List<MarketPrice> calculateBestPrice(List<ExchangeTicker> tickers) {
        Instant fetchTime = Instant.now();
        Map<String, MarketPrice> map = new HashMap<>();

        for (ExchangeTicker ticket : tickers) {
            MarketPrice mp = map.computeIfAbsent(ticket.getSymbol(), k ->
                    MarketPrice.builder()
                            .symbol(k)
                            .bestBid(ticket.getBid())
                            .bestAsk(ticket.getAsk())
                            .timestamp(fetchTime)
                            .build()
            );

            if (ticket.getBid().compareTo(mp.getBestBid()) > 0) {
                mp.setBestBid(ticket.getBid());
            }

            if (ticket.getAsk().compareTo(mp.getBestAsk()) < 0) {
                mp.setBestAsk(ticket.getAsk());
            }
        }

        return new ArrayList<>(map.values());
    }

}

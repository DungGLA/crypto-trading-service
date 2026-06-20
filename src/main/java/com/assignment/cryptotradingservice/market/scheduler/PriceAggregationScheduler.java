package com.assignment.cryptotradingservice.market.scheduler;

import com.assignment.cryptotradingservice.market.service.PriceAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceAggregationScheduler {
    private final PriceAggregationService priceAggregationService;

    @Scheduled(fixedRate = 10000)
    public void aggregatePrice() {
        try {
            System.out.println("Starting price aggregation job...");
            priceAggregationService.aggregate();
        } catch (Exception e) {
            System.out.println("Error in scheduler: " + e.getMessage());
        }
    }
}

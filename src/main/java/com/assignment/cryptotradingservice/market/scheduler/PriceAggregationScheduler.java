package com.assignment.cryptotradingservice.market.scheduler;

import com.assignment.cryptotradingservice.market.service.PriceAggregationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriceAggregationScheduler {
    private final PriceAggregationService priceAggregationService;

    @Scheduled(fixedRate = 10000)
    public void aggregatePrice() {
        try {
            log.info("Starting price aggregation job");
            priceAggregationService.aggregate();
            log.info("End price aggregation job");
        } catch (Exception e) {
            log.warn("Error in scheduler: " + e.getMessage());
        }
    }
}

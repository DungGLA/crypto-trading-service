package com.assignment.cryptotradingservice.market.client;

import com.assignment.cryptotradingservice.market.dto.BinanceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BinanceClient {
    private final WebClient webClient;

    @Value("${binance.api.url}")
    private String binanceApiUrl;

    public List<BinanceResponse> getPrices() {
        try {
            BinanceResponse[] response = webClient.get()
                    .uri(binanceApiUrl)
                    .retrieve()
                    .bodyToMono(BinanceResponse[].class)
                    .timeout(java.time.Duration.ofSeconds(3))
                    .block();
            if (response == null) {
                log.warn("Binance response is null");
                return List.of();
            }

            return Arrays.asList(response);
        } catch (Exception e) {
            log.error("Error calling Binance API", e);
            return List.of();
        }

    }

}

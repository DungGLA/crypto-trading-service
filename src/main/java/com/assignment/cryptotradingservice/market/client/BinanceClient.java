package com.assignment.cryptotradingservice.market.client;

import com.assignment.cryptotradingservice.market.dto.BinanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BinanceClient {
    private final WebClient webClient;

    private static final String URL = "https://api.binance.com/api/v3/ticker/bookTicker";

    public List<BinanceResponse> getPrices() {
        BinanceResponse[] response = webClient.get()
                .uri(URL)
                .retrieve()
                .bodyToMono(BinanceResponse[].class)
                .timeout(Duration.ofSeconds(3))
                .block();
        return response == null ? List.of() : Arrays.asList(response);
    }

}

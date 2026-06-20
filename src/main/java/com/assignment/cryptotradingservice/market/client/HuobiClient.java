package com.assignment.cryptotradingservice.market.client;

import com.assignment.cryptotradingservice.market.dto.HuobiResponse;
import com.assignment.cryptotradingservice.market.dto.HuobiTicker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HuobiClient {
    private final WebClient webClient;

    private static final String URL = "https://api.huobi.pro/market/tickers";

    public List<HuobiTicker> getPrices() {

        try {
            HuobiResponse response = webClient.get()
                    .uri(URL)
                    .retrieve()
                    .bodyToMono(HuobiResponse.class)
                    .block();

            if (response == null || !"ok".equalsIgnoreCase(response.getStatus())) {
//                log.warn("Huobi response invalid or null");
                return List.of();
            }

            if (response.getData() == null) {
                return List.of();
            }

            return response.getData();

        } catch (Exception e) {
//            log.error("Error calling Huobi API", e);
            return List.of();
        }
    }
}

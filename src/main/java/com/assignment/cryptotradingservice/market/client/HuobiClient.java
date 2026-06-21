package com.assignment.cryptotradingservice.market.client;

import com.assignment.cryptotradingservice.market.dto.HuobiResponse;
import com.assignment.cryptotradingservice.market.dto.HuobiTicker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class HuobiClient {
    private final WebClient webClient;

    @Value("${huobi.api.url}")
    private String huobiApiUrl;

    public List<HuobiTicker> getPrices() {

        try {
            HuobiResponse response = webClient.get()
                    .uri(huobiApiUrl)
                    .retrieve()
                    .bodyToMono(HuobiResponse.class)
                    .timeout(java.time.Duration.ofSeconds(3))
                    .block();

            if (response == null || !"ok".equalsIgnoreCase(response.getStatus())) {
                log.warn("Huobi response invalid or null");
                return List.of();
            }

            if (response.getData() == null) {
                return List.of();
            }

            return response.getData();

        } catch (Exception e) {
            log.error("Error calling Huobi API", e);
            return List.of();
        }
    }
}

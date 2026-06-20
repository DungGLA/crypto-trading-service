package com.assignment.cryptotradingservice.market.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExchangeTicker {
    private String symbol;

    private BigDecimal bid;

    private BigDecimal ask;

    private String exchange;
}

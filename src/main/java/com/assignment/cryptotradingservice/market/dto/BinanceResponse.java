package com.assignment.cryptotradingservice.market.dto;

import lombok.Data;

@Data
public class BinanceResponse {
    private String symbol;

    private String bidPrice;

    private String askPrice;
}

package com.assignment.cryptotradingservice.market.dto;

import lombok.Data;

import java.util.List;

@Data
public class HuobiResponse {

    private String status;

    private List<HuobiTicker> data;
}

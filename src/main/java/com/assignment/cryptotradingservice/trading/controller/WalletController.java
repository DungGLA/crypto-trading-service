package com.assignment.cryptotradingservice.trading.controller;

import com.assignment.cryptotradingservice.common.response.PageResponse;
import com.assignment.cryptotradingservice.trading.dto.WalletBalanceResponse;
import com.assignment.cryptotradingservice.trading.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping
    public PageResponse<WalletBalanceResponse> getWallet(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return walletService.getWallet(pageable);
    }
}

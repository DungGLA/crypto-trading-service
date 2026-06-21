package com.assignment.cryptotradingservice.trading.service;

import com.assignment.cryptotradingservice.common.response.PageResponse;
import com.assignment.cryptotradingservice.trading.dto.WalletBalanceResponse;
import com.assignment.cryptotradingservice.trading.entity.Wallet;
import org.springframework.data.domain.Pageable;

public interface WalletService {
    void save(Wallet wallet);

    Wallet findByUserIdAndAssetOrCreate(Long userId, String asset);

    PageResponse<WalletBalanceResponse> getWallet(Pageable pageable);
}

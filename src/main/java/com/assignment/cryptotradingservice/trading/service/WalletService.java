package com.assignment.cryptotradingservice.trading.service;

import com.assignment.cryptotradingservice.trading.dto.WalletBalanceResponse;
import com.assignment.cryptotradingservice.trading.entity.Wallet;

import java.util.List;

public interface WalletService {
    void save(Wallet wallet);

    Wallet findByUserIdAndAsset(Long userId, String asset);

    List<WalletBalanceResponse> getWallet();
}

package com.assignment.cryptotradingservice.trading.service;

import com.assignment.cryptotradingservice.trading.dto.WalletBalanceResponse;
import com.assignment.cryptotradingservice.trading.entity.Wallet;
import com.assignment.cryptotradingservice.trading.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public void save(Wallet wallet) {
        walletRepository.save(wallet);
    }

    public List<WalletBalanceResponse> getWallet(Long userId) {

        List<Wallet> wallets =
                walletRepository.findByUserId(userId);

        return wallets.stream()
                .map(w -> WalletBalanceResponse.builder()
                        .asset(w.getAsset())
                        .balance(w.getBalance())
                        .build())
                .toList();
    }
}

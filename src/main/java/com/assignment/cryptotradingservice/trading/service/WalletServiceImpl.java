package com.assignment.cryptotradingservice.trading.service;

import com.assignment.cryptotradingservice.common.exception.UnauthorizedAccessException;
import com.assignment.cryptotradingservice.common.helper.UserContext;
import com.assignment.cryptotradingservice.trading.dto.WalletBalanceResponse;
import com.assignment.cryptotradingservice.trading.entity.Wallet;
import com.assignment.cryptotradingservice.trading.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserContext userContext;

    @Override
    public void save(Wallet wallet) {
        walletRepository.save(wallet);
    }

    @Override
    public Wallet findByUserIdAndAsset(Long userId, String asset) {
        return walletRepository.findByUserIdAndAsset(userId, asset);
    }

    @Override
    public List<WalletBalanceResponse> getWallet() {
        Long userId = userContext.getUserId();
        if (userId == null) {
            throw new UnauthorizedAccessException();
        }

        List<Wallet> wallets = walletRepository.findByUserId(userId);

        return wallets.stream()
                .map(w -> WalletBalanceResponse.builder()
                        .asset(w.getAsset())
                        .balance(w.getBalance())
                        .build())
                .toList();
    }
}

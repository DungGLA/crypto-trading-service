package com.assignment.cryptotradingservice.trading.service;

import com.assignment.cryptotradingservice.common.exception.UnauthorizedAccessException;
import com.assignment.cryptotradingservice.common.helper.PageResponseMapper;
import com.assignment.cryptotradingservice.common.helper.UserContext;
import com.assignment.cryptotradingservice.common.response.PageResponse;
import com.assignment.cryptotradingservice.trading.dto.WalletBalanceResponse;
import com.assignment.cryptotradingservice.trading.entity.Wallet;
import com.assignment.cryptotradingservice.trading.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
    public Wallet findByUserIdAndAssetOrCreate(Long userId, String asset) {
        Wallet wallet = walletRepository.findByUserIdAndAsset(userId, asset);
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setUserId(userId);
            wallet.setAsset(asset);
            wallet.setBalance(BigDecimal.valueOf(0.0));
            walletRepository.save(wallet);
        }
        return wallet;
    }

    @Override
    public PageResponse<WalletBalanceResponse> getWallet(Pageable pageable) {
        Long userId = userContext.getUserId();
        if (userId == null) {
            throw new UnauthorizedAccessException();
        }

        Page<WalletBalanceResponse> pageWallets = walletRepository
                .findByUserIdOrderByAssetAsc(userId, pageable)
                .map(w -> WalletBalanceResponse.builder()
                        .asset(w.getAsset())
                        .balance(w.getBalance())
                        .build());

        return PageResponseMapper.from(pageWallets);
    }
}

package com.assignment.cryptotradingservice.trading.repository;

import com.assignment.cryptotradingservice.trading.entity.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
        Wallet findByUserIdAndAsset(Long userId, String asset);

        Page<Wallet> findByUserIdOrderByAssetAsc(Long userId, Pageable pageable);
}
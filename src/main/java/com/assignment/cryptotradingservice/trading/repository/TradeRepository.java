package com.assignment.cryptotradingservice.trading.repository;

import com.assignment.cryptotradingservice.trading.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
}
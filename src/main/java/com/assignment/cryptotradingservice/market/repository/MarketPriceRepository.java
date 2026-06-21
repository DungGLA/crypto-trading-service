package com.assignment.cryptotradingservice.market.repository;

import com.assignment.cryptotradingservice.market.entity.MarketPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketPriceRepository extends JpaRepository<MarketPrice, Long> {

    @Query("""
        SELECT mp
        FROM MarketPrice mp
        WHERE mp.timestamp = (
            SELECT MAX(m.timestamp)
            FROM MarketPrice m
            WHERE m.symbol = mp.symbol
        )
        AND (:filterBySymbols = false OR mp.symbol IN :symbols)
    """)
    List<MarketPrice> findLatestPrices(
            @Param("symbols") List<String> symbols,
            @Param("filterBySymbols") boolean filterBySymbols
    );
}
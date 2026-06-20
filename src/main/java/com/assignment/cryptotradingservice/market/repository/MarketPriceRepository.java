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
        WHERE mp.id IN (
            SELECT MAX(m.id)
            FROM MarketPrice m
            WHERE (:symbols IS NULL OR m.symbol IN :symbols)
            GROUP BY m.symbol
        )
    """)
    List<MarketPrice> findLatestPrices(
            @Param("symbols") List<String> symbols
    );
}
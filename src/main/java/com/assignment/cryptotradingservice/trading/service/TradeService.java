package com.assignment.cryptotradingservice.trading.service;

import com.assignment.cryptotradingservice.common.exception.UnauthorizedAccessException;
import com.assignment.cryptotradingservice.common.helper.PageResponseMapper;
import com.assignment.cryptotradingservice.common.helper.UserContext;
import com.assignment.cryptotradingservice.common.response.PageResponse;
import com.assignment.cryptotradingservice.market.dto.PriceResponse;
import com.assignment.cryptotradingservice.market.service.PriceAggregationService;
import com.assignment.cryptotradingservice.trading.dto.TradeExecutionInput;
import com.assignment.cryptotradingservice.trading.dto.TradeExecutionResult;
import com.assignment.cryptotradingservice.trading.dto.TradeRequest;
import com.assignment.cryptotradingservice.trading.dto.TradeResponse;
import com.assignment.cryptotradingservice.trading.entity.Trade;
import com.assignment.cryptotradingservice.trading.entity.Wallet;
import com.assignment.cryptotradingservice.trading.repository.TradeRepository;
import com.assignment.cryptotradingservice.trading.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {
    // TODO: shouldn't call to market service directly
    private final PriceAggregationService priceAggregationService;
    private final WalletService walletService;
    private final TradeExecutionEngine executionEngine;
    private final TradeRepository tradeRepository;
    private final UserContext userContext;

    public PageResponse<TradeResponse> getHistory(Pageable pageable) {
        Long userId = userContext.getUserId();
        if (userId == null) {
            throw new UnauthorizedAccessException();
        }

        Page<TradeResponse> page = tradeRepository
                .findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(trade -> TradeResponse.builder()
                        .symbol(trade.getSymbol())
                        .price(trade.getPrice())
                        .quantity(trade.getQuantity())
                        .total(trade.getTotal())
                        .timestamp(trade.getCreatedAt())
                        .build());
        return PageResponseMapper.from(page);
    }

    @Transactional
    public TradeResponse executeTrade(TradeRequest req) {
        Long userId = userContext.getUserId();
        if (userId == null) {
            throw new UnauthorizedAccessException();
        }

        // TODO: catch exception if empty
        PriceResponse latestBestPrices = priceAggregationService.getLatestBestPrice(List.of(req.getSymbol())).get(0);


        Wallet usdt = walletService.findByUserIdAndAsset(userId, "USDT");

        Wallet crypto = walletService.findByUserIdAndAsset(userId, req.getSymbol().replace("USDT", ""));

        TradeExecutionInput input = buildExecutionInput(req, latestBestPrices, usdt, crypto, userId);

        TradeExecutionResult result = executionEngine.execute(input);

        saveWallets(result);

        Trade trade = saveTrade(req, result, userId);

        return TradeResponse.builder()
                .symbol(req.getSymbol())
                .tradeType(req.getTradeType())
                .price(result.getPrice())
                .quantity(req.getQuantity())
                .total(result.getTotal())
                .timestamp(trade.getCreatedAt())
                .build();
    }

    private TradeExecutionInput buildExecutionInput(
            TradeRequest req,
            PriceResponse price,
            Wallet usdtWallet,
            Wallet cryptoWallet,
            Long userId
    ) {
        return TradeExecutionInput.builder()
                .userId(userId)
                .symbol(req.getSymbol())
                .tradeType(req.getTradeType())
                .quantity(req.getQuantity())
                .bestBid(price.getBestBid())
                .bestAsk(price.getBestAsk())
                .usdtWallet(usdtWallet)
                .cryptoWallet(cryptoWallet)
                .build();
    }

    private void saveWallets(TradeExecutionResult result) {
        walletService.save(result.getUpdatedUsdt());
        walletService.save(result.getUpdatedCrypto());
    }

    private Trade saveTrade(TradeRequest req, TradeExecutionResult result, Long userId) {
        Trade trade = new Trade();

        trade.setUserId(userId);
        trade.setSymbol(req.getSymbol());
        trade.setSide(req.getTradeType());
        trade.setQuantity(req.getQuantity());
        trade.setPrice(result.getPrice());
        trade.setTotal(result.getTotal());
        trade.setCreatedAt(Timestamp.from(Instant.now()));

        return tradeRepository.save(trade);
    }
}

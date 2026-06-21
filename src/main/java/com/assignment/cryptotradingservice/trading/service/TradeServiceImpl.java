package com.assignment.cryptotradingservice.trading.service;

import com.assignment.cryptotradingservice.common.exception.TradingBadRequestException;
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
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final PriceAggregationService priceAggregationService;
    private final WalletService walletService;
    private final TradeExecutionEngine executionEngine;
    private final TradeRepository tradeRepository;
    private final UserContext userContext;

    @Override
    public PageResponse<TradeResponse> getHistory(Pageable pageable) {
        Long userId = userContext.getUserId();
        if (userId == null) {
            throw new UnauthorizedAccessException();
        }

        Page<TradeResponse> page = tradeRepository
                .findByUserIdOrderByTradeTimeDesc(userId, pageable)
                .map(trade -> TradeResponse.builder()
                        .symbol(trade.getSymbol())
                        .price(trade.getPrice())
                        .quantity(trade.getQuantity())
                        .total(trade.getTotal())
                        .tradeType(trade.getTradeType())
                        .tradeTime(trade.getTradeTime())
                        .build());
        return PageResponseMapper.from(page);
    }

    @Override
    @Transactional
    public TradeResponse executeTrade(TradeRequest req) {
        validateTradeRequest(req);

        Long userId = userContext.getUserId();
        String symbol = req.getSymbol();

        List<PriceResponse> latestBestPrices = priceAggregationService.getLatestBestPrice(List.of(symbol));
        if (CollectionUtils.isEmpty(latestBestPrices)) {
            throw new TradingBadRequestException("Price data not available for symbol: " + symbol);
        }

        PriceResponse latestBestPrice = latestBestPrices.get(0);
        String cryptoAsset = req.getSymbol().replace("USDT", "");
        Wallet usdt = walletService.findByUserIdAndAssetOrCreate(userId, "USDT");
        Wallet crypto = walletService.findByUserIdAndAssetOrCreate(userId, cryptoAsset);

        TradeExecutionInput input = buildExecutionInput(req, latestBestPrice, usdt, crypto, userId);

        TradeExecutionResult result = executionEngine.execute(input);

        saveWallets(result);

        Trade trade = saveTrade(req, result, userId);

        return TradeResponse.builder()
                .symbol(req.getSymbol())
                .tradeType(req.getTradeType())
                .price(result.getPrice())
                .quantity(req.getQuantity())
                .total(result.getTotal())
                .tradeTime(trade.getTradeTime())
                .build();
    }

    private void validateTradeRequest(TradeRequest req) {
        Long userId = userContext.getUserId();
        if (userId == null) {
            throw new UnauthorizedAccessException();
        }
        if (StringUtils.isEmpty(req.getSymbol())) {
            throw new TradingBadRequestException("Symbol is required");
        }
        if (StringUtils.isEmpty(req.getTradeType())) {
            throw new TradingBadRequestException("Trade type is required");
        }
        if (req.getQuantity() == null || req.getQuantity().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new TradingBadRequestException("Quantity must be greater than zero");
        }
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
        trade.setTradeType(req.getTradeType());
        trade.setQuantity(req.getQuantity());
        trade.setPrice(result.getPrice());
        trade.setTotal(result.getTotal());
        trade.setTradeTime(Instant.now());

        return tradeRepository.save(trade);
    }
}

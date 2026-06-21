CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255)
);

INSERT INTO users(id, username)
VALUES (1, 'test-user');

CREATE INDEX idx_wallet_user_asset
ON wallet(user_id, asset);

CREATE INDEX idx_trade_user_trade_time
ON trade(user_id, trade_time);

CREATE INDEX idx_market_price_symbol_timestamp
ON market_price(symbol, timestamp);

INSERT INTO wallet(user_id, asset, balance)
VALUES (1, 'USDT', 50000);

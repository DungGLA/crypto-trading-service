CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255)
);

INSERT INTO users(id, username)
VALUES (1, 'test-user');

INSERT INTO wallet(user_id, asset, balance)
VALUES (1, 'USDT', 50000);

INSERT INTO wallet(user_id, asset, balance)
VALUES (1, 'BTC', 0);

INSERT INTO wallet(user_id, asset, balance)
VALUES (1, 'ETH', 0);
# Crypto Trading Service

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](./)
[![Java](https://img.shields.io/badge/java-21%2B-blue)](https://adoptopenjdk.net/)
[![License: MIT](https://img.shields.io/badge/license-MIT-green)](LICENSE)

A Spring Boot application for managing and trading cryptocurrencies, providing a RESTful API and automated market updates.

---

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
- [Usage Examples](#usage-examples)
- [Contributing](#contributing)

---

## Features

- RESTful API for cryptocurrency trading operations
- Scheduled tasks for market data updates
- Modular, extensible codebase using Java, Spring Boot, H2

---

## Getting Started

# 🏗 Project Structure (Feature-based Package)

The project follows a feature-based architecture:

com.assignment.cryptotradingservice
│
├── common
├── market
├── trading
└── data.sql

---

## ⚙️ Common Package

Contains shared components used across the application:

- Utilities
- Constants
- Shared models
- Exception handling

---

## 📈 Market Package

Handles all market-related operations.

### Responsibilities:
- Fetch ticker data from external exchanges (Binance, Huobi)
- Aggregate best bid / best ask prices
- Store latest market snapshot into database
- Provide API to query latest market prices

---

### Task 1: Market Data Cron Job

- Runs every 10 seconds
- Fetches BTC/USDT and ETH/USDT prices from Binance and Huobi
- Computes:
    - Best Bid = highest bid
    - Best Ask = lowest ask
- Saves aggregated result into `market_price` table

---

### Task 2: Get Latest Prices API

GET /api/prices

Optional:
GET /api/prices?symbols=BTCUSDT,ETHUSDT

Description:
- Returns latest aggregated market prices
- Can filter by specific symbols

---

## 💱 Trading Package

Handles trading operations, wallet management, and trade history.

---

### Task 3: Execute Trade

POST /api/trades

Request body:

{
"symbol": "BTCUSDT",
"quantity": 0.5,
"tradeType": "BUY"
}

Description:
- Executes BUY or SELL trade
- Uses latest best aggregated price from market_price
- Updates user wallet balance accordingly
- Saves trade record into trade table

---

### Task 4: Get Wallet Balance

GET /api/wallets

Description:
- Returns wallet balance of user
- Based on wallet table data
- Default user: user_id = 1

---

### Task 5: Get Trade History

GET /api/trades/history

Description:
- Returns all trades executed by the user
- Includes BUY / SELL history with price, quantity, timestamp

---

# 🗄 Database (H2 In-Memory)

The application uses H2 in-memory database.

---

## 👤 users table

| column | type |
|--------|------|
| id | Long |
| username | String |

Default user:

id = 1
username = test-user

---

## 📈 market_price table

| column | type |
|--------|------|
| id | Long |
| symbol | String |
| best_bid | BigDecimal |
| best_ask | BigDecimal |
| timestamp | Instant |

- Updated every 10 seconds by cron job

---

## 💱 trade table

| column | type |
|--------|------|
| id | Long |
| user_id | Long |
| symbol | String |
| trade_type | String (BUY/SELL) |
| quantity | BigDecimal |
| price | BigDecimal |
| total | BigDecimal |
| trade_time | Instant |

- Inserted on every trade execution

---

## 💰 wallet table

| column | type |
|--------|------|
| id | Long |
| user_id | Long |
| asset | String |
| balance | BigDecimal |

Initial balance:

50,000 USDT for user_id = 1

---

# 📦 data.sql

The data.sql file initializes:

- Sample user data
- Initial wallet balance
- Required indexes for performance

# 🚀 Application Flow

## Market Data Flow

Cron Job (10s)
→ Fetch Binance + Huobi
→ Compute best bid/ask
→ Save to market_price

---

## Trading Flow

POST /api/trades
→ Get latest market price
→ Calculate trade value
→ Update wallet
→ Save trade record

---

## Wallet Flow

Trade executed
→ Update wallet balance
→ Persist to wallet table

### Installation

Clone the repository:
```sh
git clone https://github.com/DungGLA/crypto-trading-service.git
cd crypto-trading-service
```

Build the project:
```sh
mvn clean install
```

Run the application:
```sh
mvn spring-boot:run
```


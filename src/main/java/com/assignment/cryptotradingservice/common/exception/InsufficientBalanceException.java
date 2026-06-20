package com.assignment.cryptotradingservice.common.exception;

import com.assignment.cryptotradingservice.common.enumeric.ErrorCode;

public class InsufficientBalanceException extends BusinessException {
    private static final String DEFAULT_MESSAGE = "Insufficient balance";

    public InsufficientBalanceException() {
        this(DEFAULT_MESSAGE);
    }

    public InsufficientBalanceException(String message) {
        super(ErrorCode.INSUFFICIENT_BALANCE, message);
    }
}

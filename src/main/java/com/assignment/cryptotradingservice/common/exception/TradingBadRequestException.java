package com.assignment.cryptotradingservice.common.exception;

import com.assignment.cryptotradingservice.common.enumeric.ErrorCode;

public class TradingBadRequestException extends BusinessException {
    private static final String DEFAULT_MESSAGE = "Bad request exception occurred. Please check the request and try again.";

    public TradingBadRequestException() {
        this(DEFAULT_MESSAGE);
    }

    public TradingBadRequestException(String message) {
        super(ErrorCode.INVALID_REQUEST, message);
    }
}
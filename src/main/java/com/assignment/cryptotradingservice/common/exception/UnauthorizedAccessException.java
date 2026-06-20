package com.assignment.cryptotradingservice.common.exception;

import com.assignment.cryptotradingservice.common.enumeric.ErrorCode;

public class UnauthorizedAccessException extends BusinessException {
    private static final String DEFAULT_MESSAGE = "Unauthorized access";

    public UnauthorizedAccessException() {
        this(DEFAULT_MESSAGE);
    }

    public UnauthorizedAccessException(String message) {
        super(ErrorCode.UNAUTHORIZED_ACCESS, message);
    }
}

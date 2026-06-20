package com.assignment.cryptotradingservice.common.exception;

import com.assignment.cryptotradingservice.common.enumeric.ErrorCode;
import com.assignment.cryptotradingservice.common.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex,
            HttpServletRequest request
    ) {

        log.warn(
                "BusinessException: code={}, message={}, path={}",
                ex.getErrorCode(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .code(ex.getErrorCode().name())
                        .message(ex.getMessage())
                        .timestamp(Instant.now())
                        .path(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknownException(
            Exception ex,
            HttpServletRequest request
    ) {

        log.error(
                "Unhandled exception at path={}",
                request.getRequestURI(),
                ex
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .code(ErrorCode.INTERNAL_SERVER_ERROR.name())
                        .message("Unexpected error occurred")
                        .timestamp(Instant.now())
                        .path(request.getRequestURI())
                        .build());
    }
}
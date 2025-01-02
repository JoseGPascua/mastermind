package com.example.mastermind.models;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Represents a pattern for the range that a given difficulty is allowed to use for the random number combination
 */
public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;
    private HttpStatus status;

    public ErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

package com.interviewplatform.backend.bot.exception;

import org.springframework.http.HttpStatus;

/**
 * Generic application exception carrying an HTTP status, thrown from
 * services for expected error conditions (bad credentials, not found, etc).
 */
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

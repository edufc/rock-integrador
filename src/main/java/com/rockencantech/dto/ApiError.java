package com.rockencantech.dto;

import java.time.Instant;

public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        Object details
) {
    public ApiError(int status, String error, String message, Object details) {
        this(Instant.now(), status, error, message, details);
    }
}
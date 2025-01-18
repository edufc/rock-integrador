package com.rockencantech.dto;

public record ValidationError(
        String field,
        Object rejectedValue,
        String error
) {}
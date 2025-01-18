package com.rockencantech.dto;

import jakarta.validation.constraints.NotNull;

public record Customer(@NotNull String id, String phone) {
    
}

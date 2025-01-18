package com.rockencantech.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record Item(@NotNull String id,
        @NotNull @DecimalMin(value = "0.1", inclusive = true, message = "Quantity must be greater than 0.1") double quantity,
        @NotNull double price) {

}

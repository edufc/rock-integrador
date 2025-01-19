package com.rockencantech.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record Ticket(@NotNull Customer customer, UUID id, @NotNull double amount,
        @NotNull List<Item> items) {

}

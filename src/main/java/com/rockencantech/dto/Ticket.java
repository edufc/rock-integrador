package com.rockencantech.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record Ticket(@NotNull Customer customerRequest, @NotNull String id, @NotNull double amount,
        @NotNull List<Item> items) {

}

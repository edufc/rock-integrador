package com.rockencantech.integration.dto;

public record BnexRequest(String operation, String customerId, String customerPhone, String ticketId, double amount, String item1Id,
                double item1Value,
                String item2Id,
                double item2Value) {

        public BnexRequest(String operation, String customerId, String customerPhone) {
                this(operation, customerId, customerPhone, null, 0, null, 0, null, 0);
        }
}

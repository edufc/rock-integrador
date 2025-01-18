package com.rockencantech.service;

import org.springframework.http.ResponseEntity;

import com.rockencantech.dto.Customer;
import com.rockencantech.dto.Ticket;

public interface PdvService {
    void identify(Customer customer);

    void authorize(Ticket ticket);

    void register(Ticket ticket);
}

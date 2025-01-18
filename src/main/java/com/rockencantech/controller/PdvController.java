package com.rockencantech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rockencantech.dto.Customer;
import com.rockencantech.dto.Ticket;
import com.rockencantech.service.PdvService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pdv")
@RequiredArgsConstructor
class PdvController {

    private final PdvService pdvService;

    @PostMapping("/identify")
    public ResponseEntity<Object> identify(@RequestBody @Valid Customer customer) {

        
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/authorize")
    public ResponseEntity<Object> authorize(@RequestBody @Valid Ticket ticket) {
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid Ticket ticket) {
        return ResponseEntity.ok(ticket);
    }
}
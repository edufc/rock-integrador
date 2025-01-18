package com.rockencantech.service;

import org.springframework.stereotype.Service;

import com.rockencantech.dto.Customer;
import com.rockencantech.dto.Ticket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PdvServiceImpl implements PdvService {@Override
    public void identify(Customer customer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'identify'");
    }

    @Override
    public void authorize(Ticket ticket) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'authorize'");
    }

    @Override
    public void register(Ticket ticket) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

}

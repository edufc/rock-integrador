package com.rockencantech.service;

import com.rockencantech.dto.Customer;
import com.rockencantech.dto.Pdv;
import com.rockencantech.dto.Ticket;

public interface PdvService {
    Pdv identify(Customer customer);

    Pdv authorize(Ticket ticket);

    Pdv register(Ticket ticket);
}

package com.rockencantech.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rockencantech.dto.Customer;
import com.rockencantech.dto.Pdv;
import com.rockencantech.dto.Ticket;
import com.rockencantech.enums.TicketStatus;
import com.rockencantech.exception.ServiceException;
import com.rockencantech.integration.BnexService;
import com.rockencantech.integration.dto.BnexRequest;
import com.rockencantech.integration.dto.BnexResponse;
import com.rockencantech.repository.TicketRepository;
import com.rockencantech.repository.entity.TicketEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PdvServiceImpl implements PdvService {

    private final BnexService bnexService;
    private final TicketRepository ticketRepository;

    @Override
    public Pdv identify(Customer customer) {
        BnexResponse response = bnexService.identify(toBnexRequest(customer));

        return new Pdv(null, null, response);
    }

    @Override
    public Pdv authorize(Ticket ticket) {
        TicketEntity ticketEntity = getInProgressTicket(ticket.id()).orElseGet(() -> createTicket(ticket));

        BnexResponse response = bnexService.authorize(toBnexRequest("autorizar", ticket, ticketEntity.getId()));
        return new Pdv(ticketEntity.getId(), ticketEntity.getStatus().name(), response);
    }

    @Override
    public Pdv register(Ticket ticket) {
        TicketEntity ticketEntity = getInProgressTicket(ticket.id())
                .orElseThrow(() -> new ServiceException(String.format("Ticket %s not found", ticket.id().toString())));

        BnexResponse response = bnexService.register(toBnexRequest("registrar", ticket, ticketEntity.getId()));

        ticketEntity.setStatus(TicketStatus.FINALIZED);
        ticketRepository.save(ticketEntity);

        return new Pdv(ticketEntity.getId(), ticketEntity.getStatus().name(), response);
    }

    private Optional<TicketEntity> getInProgressTicket(UUID ticketId) {
        return ticketRepository.findById(ticketId)
                .map(entity -> {
                    validateInProgressTicket(entity);
                    return entity;
                });
    }

    private BnexRequest toBnexRequest(Customer customer) {
        return new BnexRequest("identificar", customer.id(), customer.phone());
    }

    private BnexRequest toBnexRequest(String operation, Ticket ticket, UUID ticketId) {
        String item1Id = ticket.items().get(0).id();
        double item1Value = ticket.items().get(0).price();

        String item2Id = null;
        double item2Value = 0;

        if (ticket.items().size() > 1) {
            item2Id = ticket.items().get(1).id();
            item2Value = ticket.items().get(1).price();
        }

        return new BnexRequest(operation, ticket.customer().id(), ticket.customer().phone(), ticketId.toString(),
                ticket.amount(), item1Id, item1Value, item2Id, item2Value);
    }

    private TicketEntity createTicket(Ticket ticket) {
        UUID ticketId = UUID.randomUUID();

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setId(ticketId);
        ticketEntity.setStatus(TicketStatus.IN_PROGRESS);
        ticketEntity.setCustomer(ticket.customer().id());

        ticketRepository.save(ticketEntity);

        return ticketEntity;
    }

    private void validateInProgressTicket(TicketEntity ticketEntity) {
        if (ticketEntity.getStatus() != TicketStatus.IN_PROGRESS) {
            throw new ServiceException(
                    String.format("Ticket %s is not %s", ticketEntity.getId().toString(),
                            TicketStatus.IN_PROGRESS.name()));
        }
    }
}

package com.rockencantech.repository.entity;

import java.util.UUID;

import com.rockencantech.enums.TicketStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ticket")
public class TicketEntity {
    
    @Id    
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(name = "customer")
    private String customer;
}

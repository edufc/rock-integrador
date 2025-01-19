package com.rockencantech.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rockencantech.enums.TicketStatus;
import com.rockencantech.repository.entity.TicketEntity;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {

    Optional<TicketEntity> findByIdAndStatus(UUID id, TicketStatus status);
}
package com.bolsa.repository;

import com.bolsa.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface MovementRepository extends JpaRepository<Movement, UUID> {

    List<Movement> findByAccountId(UUID accountId);

    List<Movement> findByAccountIdAndDateBetween(
            UUID accountId,
            LocalDateTime start,
            LocalDateTime end
    );
}
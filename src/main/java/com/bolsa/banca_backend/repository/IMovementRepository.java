package com.bolsa.banca_backend.repository;

import com.bolsa.banca_backend.entity.Movement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

import java.util.UUID;

public interface IMovementRepository extends JpaRepository<Movement, UUID> {
    List<Movement> findByAccountId(UUID accountId);

    @Query("""
        select m
        from Movement m
        join fetch m.account a
        join fetch a.customer c
        where c.id = :customerId
          and m.movementDate between :from and :to
        order by m.movementDate asc, m.createdAt asc
    """)
    List<Movement> findReportMovements(@Param("customerId") UUID customerId,
                                       @Param("from") LocalDate from,
                                       @Param("to") LocalDate to);
}

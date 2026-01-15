package com.bolsa.banca_backend.repository;

import com.bolsa.banca_backend.entity.Movement;
import com.bolsa.banca_backend.utils.MovementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMovementRepository extends JpaRepository<Movement, UUID> {

    List<Movement> findByAccountIdOrderByMovementDateDesc(UUID accountId);



    @Query("""
    select m
    from Movement m
    where m.account.customer.id = :customerId
      and m.movementDate between :from and :to
    order by m.movementDate asc
""")
    List<Movement> findByCustomerAndDateRange(UUID customerId, LocalDate from, LocalDate to);


    @Query("""
        select coalesce(sum(abs(m.amount)), 0)
        from Movement m
        where m.account.id = :accountId
          and m.movementType = :type
          and m.movementDate = :date
    """)
    BigDecimal sumDailyAbsByType(UUID accountId, MovementType type, LocalDate date);
}

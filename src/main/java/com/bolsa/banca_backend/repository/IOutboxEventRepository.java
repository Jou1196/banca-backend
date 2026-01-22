package com.bolsa.banca_backend.repository;

import com.bolsa.banca_backend.entity.OutboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IOutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID> {

    @Query("""
        select e from OutboxEventEntity e
        where e.status = 'PENDING'
          and e.availableAt <= :now
        order by e.createdAt asc
        """)
    List<OutboxEventEntity> findPending(@Param("now") LocalDateTime now);

    @Modifying
    @Transactional
    @Query("""
      update OutboxEventEntity e
      set e.status = 'PROCESSING'
      where e.id = :id and e.status = 'PENDING'
    """)
    int markProcessing(@Param("id") UUID id);
}

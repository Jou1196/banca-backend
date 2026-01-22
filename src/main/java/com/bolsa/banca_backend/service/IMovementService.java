package com.bolsa.banca_backend.service;


import com.bolsa.banca_backend.dto.MovementCreateRequest;
import com.bolsa.banca_backend.dto.MovementResponse;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Interface IMovementService
 */
public interface IMovementService {
    MovementResponse create(MovementCreateRequest req);
    List<MovementResponse> getByAccount(UUID accountId);
    List<MovementResponse> getAll();
    List<MovementResponse> getByCustomerAndDates(UUID customerId, LocalDate from, LocalDate to);
}

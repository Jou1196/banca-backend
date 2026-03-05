package com.bolsa.service;

import com.bolsa.dto.MovementCreateRequest;
import com.bolsa.entity.Movement;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IMovementService {
    Movement create(MovementCreateRequest req);
    List<Movement> getByAccountId(UUID accountId);
    List<Movement> getByAccountAndDates(UUID accountId, LocalDate from, LocalDate to);
}
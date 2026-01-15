package com.bolsa.banca_backend.service;


import com.bolsa.banca_backend.dto.MovementCreateRequest;
import com.bolsa.banca_backend.dto.MovementResponse;


import java.util.List;
import java.util.UUID;

public interface IMovementService {
    MovementResponse create(MovementCreateRequest req);

    List<MovementResponse> findByAccount(UUID accountId);
}

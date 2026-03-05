package com.bolsa.service;

import com.bolsa.dto.MovementApplyRequest;
import com.bolsa.dto.MovementApplyResponse;

import java.util.UUID;

public interface InternalAccountService {
    MovementApplyResponse applyMovement(UUID accountId, MovementApplyRequest req);
}
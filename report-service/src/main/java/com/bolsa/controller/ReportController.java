package com.bolsa.controller;



import com.bolsa.dto.ReportResponse;
import com.bolsa.service.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReportController {

    private final IReportService reportService;

    @GetMapping("/estado-cuenta")
    public ResponseEntity<ReportResponse> estadoCuenta(
            @RequestParam UUID customerId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        return ResponseEntity.ok(reportService.statusAccount(customerId, from, to));
    }
}
package com.bolsa.banca_backend.controller;



import com.bolsa.banca_backend.dto.ReportResponse;
import com.bolsa.banca_backend.service.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportController {

    private final IReportService reportService;

    /**
     * Reporte "Estado de cuenta" por cliente y rango de fechas.
     *
     * Ejemplo:
     *  GET /api/reportes/estado-cuenta?customerId=UUID&from=2022-02-08&to=2022-10-02
     */
    @GetMapping("/estado-cuenta")
    public ResponseEntity<ReportResponse> estadoCuenta(
            @RequestParam UUID customerId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        return ResponseEntity.ok(reportService.statusAccount(customerId, from, to));
    }
}

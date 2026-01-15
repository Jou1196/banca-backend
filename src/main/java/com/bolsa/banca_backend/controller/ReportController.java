package com.bolsa.banca_backend.controller;



import com.bolsa.banca_backend.dto.ReportResponse;
import com.bolsa.banca_backend.service.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {

    private final IReportService reportService;

    @GetMapping
    public ResponseEntity<ReportResponse> getReport(
            @RequestParam UUID customerId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        return ResponseEntity.ok(reportService.getCustomerReport(customerId, from, to));
    }
}

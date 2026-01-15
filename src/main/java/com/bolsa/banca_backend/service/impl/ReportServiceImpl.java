package com.bolsa.banca_backend.service.impl;



import com.bolsa.banca_backend.dto.ReportAccountDto;
import com.bolsa.banca_backend.dto.ReportResponse;
import com.bolsa.banca_backend.entity.Account;
import com.bolsa.banca_backend.entity.Customer;
import com.bolsa.banca_backend.entity.Movement;
import com.bolsa.banca_backend.repository.IAccountRepository;
import com.bolsa.banca_backend.repository.ICustomerRepository;
import com.bolsa.banca_backend.repository.IMovementRepository;
import com.bolsa.banca_backend.service.IReportService;
import com.bolsa.banca_backend.utils.MovementType;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements IReportService {

    private final ICustomerRepository customerRepository;
    private final IAccountRepository accountRepository;
    private final IMovementRepository movementRepository;

    @Override
    public ReportResponse getCustomerReport(UUID customerId, LocalDate from, LocalDate to) {
        if (customerId == null) throw new IllegalArgumentException("Cliente requerido");
        if (from == null || to == null) throw new IllegalArgumentException("Para requerido");
        if (from.isAfter(to)) throw new IllegalArgumentException("Fallo al ingresar ");

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        List<Account> accounts = accountRepository.findByCustomerId(customerId);
        List<Movement> movements = movementRepository.findByCustomerAndDateRange(customerId, from, to);

        BigDecimal totalCredit = movements.stream()
                .filter(m -> m.getMovementType() == MovementType.CREDIT)
                .map(Movement::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDebit = movements.stream()
                .filter(m -> m.getMovementType() == MovementType.DEBIT)
                .map(m -> m.getAmount().abs())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<ReportAccountDto> accountDtos = accounts.stream()
                .map(a -> new ReportAccountDto(
                        a.getId(),
                        a.getAccountNumber(),
                        a.getAccountType(),
                        getCurrentBalance(a)
                ))
                .toList();

        String pdfBase64 = Base64.getEncoder().encodeToString(
                generatePdf(customer, from, to, accountDtos, movements, totalCredit, totalDebit)
        );

        return new ReportResponse(
                customer.getId(),
                customer.getName(),
                from,
                to,
                accountDtos,
                totalCredit,
                totalDebit,
                pdfBase64
        );
    }

    private BigDecimal getCurrentBalance(Account account) {

        List<Movement> latest = movementRepository.findByAccountIdOrderByMovementDateDesc(account.getId());
        if (latest.isEmpty()) return account.getInitialBalance() == null ? BigDecimal.ZERO : account.getInitialBalance();
        return latest.get(0).getAvailableBalance();
    }

    private byte[] generatePdf(
            Customer customer,
            LocalDate from,
            LocalDate to,
            List<ReportAccountDto> accounts,
            List<Movement> movements,
            BigDecimal totalCredit,
            BigDecimal totalDebit
    ) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 11);

            document.add(new Paragraph("Reporte de cuenta", titleFont));
            document.add(new Paragraph("cliente: " + customer.getName(), normal));
            document.add(new Paragraph("Identificacion: " + customer.getIdentification(), normal));
            document.add(new Paragraph("Rango de fechas: " + from + " to " + to, normal));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Cuentas", new Font(Font.HELVETICA, 13, Font.BOLD)));
            PdfPTable accTable = new PdfPTable(4);
            accTable.setWidthPercentage(100);
            accTable.setWidths(new float[]{3, 3, 2, 2});

            addHeader(accTable, "Cuenta");
            addHeader(accTable, "Numero de cuenta");
            addHeader(accTable, "Tipo");
            addHeader(accTable, "Balance");

            for (ReportAccountDto a : accounts) {
                accTable.addCell(a.accountId().toString());
                accTable.addCell(a.accountNumber());
                accTable.addCell(a.accountType());
                accTable.addCell(a.currentBalance().toPlainString());
            }
            document.add(accTable);

            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Movimientos", new Font(Font.HELVETICA, 13, Font.BOLD)));

            PdfPTable mvTable = new PdfPTable(5);
            mvTable.setWidthPercentage(100);
            mvTable.setWidths(new float[]{2, 3, 2, 2, 2});

            addHeader(mvTable, "Fecha");
            addHeader(mvTable, "Cuenta");
            addHeader(mvTable, "Tipo");
            addHeader(mvTable, "Cuenta");
            addHeader(mvTable, "Balance");

            for (Movement m : movements) {
                mvTable.addCell(m.getMovementDate().toString());
                mvTable.addCell(m.getAccount().getAccountNumber());
                mvTable.addCell(m.getMovementType().name());
                mvTable.addCell(m.getAmount().abs().toPlainString());
                mvTable.addCell(m.getAvailableBalance().toPlainString());
            }

            document.add(mvTable);

            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Resumen", new Font(Font.HELVETICA, 13, Font.BOLD)));
            document.add(new Paragraph("Total Credit: " + totalCredit.toPlainString(), normal));
            document.add(new Paragraph("Total Debit: " + totalDebit.toPlainString(), normal));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalArgumentException("No se pudo generar el  PDF");
        }
    }

    private void addHeader(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(Font.HELVETICA, 11, Font.BOLD)));
        cell.setBackgroundColor(new java.awt.Color(230, 230, 230));
        cell.setPadding(6);
        table.addCell(cell);
    }
}

package com.bolsa.banca_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * Class Transaction
 */
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaction;
    private String transactionType;
    private Double amount;
    private Double initialBalance;
    private String status = "Completado"; // Estado por defecto
    private Double availableBalance;
    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;
}

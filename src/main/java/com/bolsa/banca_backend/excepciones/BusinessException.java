package com.bolsa.banca_backend.excepciones;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}

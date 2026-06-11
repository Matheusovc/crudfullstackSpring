package com.exemplo.alunoservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> tratarRuntime(RuntimeException ex) {
        String mensagem = ex.getMessage() == null ? "Erro inesperado" : ex.getMessage();
        String lower = mensagem.toLowerCase();
        HttpStatus status = (lower.contains("nao encontrad") || lower.contains("não encontrad"))
                ? HttpStatus.NOT_FOUND
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(new ApiError(
                status.value(),
                mensagem,
                LocalDateTime.now()
        ));
    }
}

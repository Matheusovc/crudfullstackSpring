package com.exemplo.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> tratarCredenciaisInvalidas(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                "Credenciais invalidas",
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> tratarConflito(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> tratarArgumentoInvalido(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> tratarRuntime(RuntimeException ex) {
        String mensagem = ex.getMessage() == null ? "Erro inesperado" : ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                mensagem,
                LocalDateTime.now()
        ));
    }
}

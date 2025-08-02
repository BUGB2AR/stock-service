package com.dev.jarmison.stock_service.exception;

import com.dev.jarmison.stock_service.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ComunicacaoMicrosservicoException.class)
    public ResponseEntity<ApiErrorResponse> handleComunicacaoMicrosservicoException(ComunicacaoMicrosservicoException ex) {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        ApiErrorResponse errorResponse = new ApiErrorResponse(LocalDateTime.now(),status.value(), "api-error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }
}
package com.dev.jarmison.stock_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ComunicacaoMicrosservicoException extends RuntimeException {
    public ComunicacaoMicrosservicoException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.dev.jarmison.stock_service.aspect;

import com.dev.jarmison.stock_service.exception.ComunicacaoMicrosservicoException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.ConnectException;

@Aspect
@Component
public class ComunicacaoAspect {
    private static final Logger log = LoggerFactory.getLogger(ComunicacaoAspect.class);

    @Around("execution(* com.dev.jarmison.stock_service.service.EstoqueService.consultarEstoque(..))")
    public Object tratarFalhaComunicacao(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (WebClientResponseException.NotFound e) {
            throw e;
        } catch (WebClientResponseException e) {
            log.error("Erro na resposta do serviço de produtos. Status: {}, Mensagem: {}", e.getStatusCode(), e.getMessage());
            throw e;
        } catch (ConnectException e) {
            log.error("O serviço de produtos está inacessível. Mensagem: {}", e.getMessage());
            throw new ComunicacaoMicrosservicoException("Não foi possível comunicar com o serviço de produtos.", e);
        } catch (Exception e) {
            log.error("Erro inesperado na comunicação com o serviço de produtos: {}", e.getMessage());
            throw new ComunicacaoMicrosservicoException("Ocorreu um erro inesperado na comunicação.", e);
        }
    }
}

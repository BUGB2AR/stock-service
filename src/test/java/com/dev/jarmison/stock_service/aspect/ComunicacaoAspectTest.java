package com.dev.jarmison.stock_service.aspect;

import com.dev.jarmison.stock_service.exception.ComunicacaoMicrosservicoException;
import com.dev.jarmison.stock_service.service.EstoqueService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para o Aspecto de Comunicação")
class ComunicacaoAspectTest {

    @InjectMocks
    private ComunicacaoAspect comunicacaoAspect;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private EstoqueService estoqueService;

    @Test
    @DisplayName("Deve prosseguir a execução sem exceção")
    void deveProsseguirSemExcecao() throws Throwable {
        when(joinPoint.proceed()).thenReturn(null);
        assertDoesNotThrow(() -> comunicacaoAspect.tratarFalhaComunicacao(joinPoint));
    }

    @Test
    @DisplayName("Deve lançar ComunicacaoMicrosservicoException em caso de falha de conexão")
    void deveLancarExcecaoEmFalhaDeConexao() throws Throwable {
        when(joinPoint.proceed()).thenThrow(new ConnectException("Connection refused"));
        assertThrows(ComunicacaoMicrosservicoException.class, () -> comunicacaoAspect.tratarFalhaComunicacao(joinPoint));
    }

    @Test
    @DisplayName("Deve lançar WebClientResponseException.NotFound em caso de status 404")
    void deveLancarExcecaoQuandoStatusFor404() throws Throwable {
        when(joinPoint.proceed()).thenThrow(new ConnectException("Not Found"));
        assertThrows(ComunicacaoMicrosservicoException.class, () -> comunicacaoAspect.tratarFalhaComunicacao(joinPoint));
    }
}
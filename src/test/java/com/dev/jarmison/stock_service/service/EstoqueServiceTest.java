package com.dev.jarmison.stock_service.service;

import com.dev.jarmison.stock_service.dto.EstoqueConsultaDto;
import com.dev.jarmison.stock_service.response.EstoqueResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para o EstoqueService")
class EstoqueServiceTest {

    @InjectMocks
    private EstoqueService estoqueService;

    @Mock
    private WebClient webClientProduto;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private EstoqueConsultaDto produto;

    @BeforeEach
    void setUp() {
        when(webClientProduto.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyLong())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);

        produto = new EstoqueConsultaDto();
        produto.setId(1L);
        produto.setNome("Teclado");
        produto.setPreco(new BigDecimal("300.00"));
        produto.setQuantidadeEmEstoque(15);
        produto.setLimiteMinimoEstoque(10);
    }

    @Test
    @DisplayName("Deve retornar estoque baixo quando quantidade é menor que o limite")
    void deveRetornarEstoqueBaixo() {
        produto.setQuantidadeEmEstoque(8);
        when(responseSpec.bodyToMono(EstoqueConsultaDto.class)).thenReturn(Mono.just(produto));

        EstoqueResponseDTO response = estoqueService.consultarEstoque(1L);

        assertTrue(response.isEstoqueBaixo());
        assertEquals("Atenção: O estoque do produto está abaixo do limite mínimo.", response.getMensagem());
    }

    @Test
    @DisplayName("Deve retornar estoque normal quando quantidade é maior que o limite")
    void deveRetornarEstoqueNormal() {
        when(responseSpec.bodyToMono(EstoqueConsultaDto.class)).thenReturn(Mono.just(produto));

        EstoqueResponseDTO response = estoqueService.consultarEstoque(1L);

        assertFalse(response.isEstoqueBaixo());
        assertEquals("Limite ok", response.getMensagem());
    }
}
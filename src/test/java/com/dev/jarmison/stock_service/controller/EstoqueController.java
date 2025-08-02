package com.dev.jarmison.stock_service.controller;

import com.dev.jarmison.stock_service.dto.EstoqueConsultaDto;
import com.dev.jarmison.stock_service.response.EstoqueResponseDTO;
import com.dev.jarmison.stock_service.service.EstoqueService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EstoqueController.class)
@DisplayName("Testes para o EstoqueController")
class EstoqueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EstoqueService estoqueService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public EstoqueService estoqueService() {
            return mock(EstoqueService.class);
        }
    }

    @Test
    @DisplayName("Deve retornar consulta de estoque com status 200 OK")
    void deveRetornarConsultaDeEstoqueComStatus200() throws Exception {
        EstoqueConsultaDto produto = new EstoqueConsultaDto();
        produto.setId(1L);
        produto.setNome("Teclado");
        produto.setQuantidadeEmEstoque(15);
        produto.setLimiteMinimoEstoque(10);

        EstoqueResponseDTO responseDTO = new EstoqueResponseDTO(produto, false, "Limite ok");
        when(estoqueService.consultarEstoque(anyLong())).thenReturn(responseDTO);

        mockMvc.perform(get("/api/estoque/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produto.id").value(1L))
                .andExpect(jsonPath("$.estoqueBaixo").value(false));
    }

    @Test
    @DisplayName("Deve retornar status 404 quando o produto não é encontrado")
    void deveRetornarStatus404QuandoProdutoNaoEncontrado() throws Exception {
        when(estoqueService.consultarEstoque(anyLong())).thenReturn(new EstoqueResponseDTO(null, false, "Produto não encontrado."));

        mockMvc.perform(get("/api/estoque/{id}", 99L))
                .andExpect(status().isNotFound());
    }
}
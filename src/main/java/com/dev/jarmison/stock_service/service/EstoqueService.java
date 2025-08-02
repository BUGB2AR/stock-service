package com.dev.jarmison.stock_service.service;

import com.dev.jarmison.stock_service.dto.EstoqueConsultaDto;
import com.dev.jarmison.stock_service.response.EstoqueResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@Service
public class EstoqueService {
    private static final Logger log = LoggerFactory.getLogger(EstoqueService.class);
    private final WebClient webClientProduto;

    public EstoqueService(WebClient webClientProduto) {
        this.webClientProduto = webClientProduto;
    }

    public EstoqueResponseDTO consultarEstoque(Long produtoId) {
        log.info("Iniciando consulta de estoque para o produto com ID: {}", produtoId);
        try {
            EstoqueConsultaDto produto = webClientProduto.get()
                    .uri("/{id}", produtoId)
                    .retrieve()
                    .bodyToMono(EstoqueConsultaDto.class)
                    .block();

            log.info("Dados do produto ID {} recebidos com sucesso. Quantidade em estoque: {}, Limite mínimo: {}",
                    produto.getId(), produto.getQuantidadeEmEstoque(), produto.getLimiteMinimoEstoque());

            boolean estoqueBaixo = produto.getQuantidadeEmEstoque() < produto.getLimiteMinimoEstoque();
            String mensagem = estoqueBaixo ? "Atenção: O estoque do produto está abaixo do limite mínimo." : "Limite ok";

            return new EstoqueResponseDTO(produto, estoqueBaixo,mensagem);
        } catch (WebClientResponseException e) {
            log.error("Erro na comunicação com o serviço de produtos. Status: {}, Mensagem: {}", e.getStatusCode(), e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new EstoqueResponseDTO(null, false,"Ação não pode ser executada no serviço de estoque.");
            }
            throw e;
        }
    }
}

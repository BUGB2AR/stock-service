package com.dev.jarmison.stock_service.controller;

import com.dev.jarmison.stock_service.response.EstoqueResponseDTO;
import com.dev.jarmison.stock_service.service.EstoqueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {
    private static final Logger log = LoggerFactory.getLogger(EstoqueController.class);
    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueResponseDTO> consultarEstoque(@PathVariable Long id) {
        log.info("Requisição GET recebida para consultar estoque do produto com ID: {}", id);
        EstoqueResponseDTO response = estoqueService.consultarEstoque(id);

        if (response.getProduto() == null) {
            log.warn("Produto com ID {} não encontrado no serviço de produtos.", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}
package com.dev.jarmison.stock_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class EstoqueConsultaDto {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidadeEmEstoque;
    private Integer limiteMinimoEstoque;
}

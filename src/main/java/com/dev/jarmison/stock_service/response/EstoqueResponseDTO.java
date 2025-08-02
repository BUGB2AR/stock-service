package com.dev.jarmison.stock_service.response;

import com.dev.jarmison.stock_service.dto.EstoqueConsultaDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstoqueResponseDTO {
    private EstoqueConsultaDto produto;
    private boolean estoqueBaixo;
    private String mensagem;
}

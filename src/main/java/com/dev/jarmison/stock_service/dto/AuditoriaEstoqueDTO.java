package com.dev.jarmison.stock_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaEstoqueDTO {
    private Long produtoId;
    private String acao;
    private String detalhes;
    private LocalDateTime dataAcao = LocalDateTime.now();
}

package com.dev.jarmison.stock_service.controller;

import com.dev.jarmison.stock_service.dto.AuditoriaEstoqueDTO;
import com.dev.jarmison.stock_service.service.AuditoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auditoria")
public class AuditoriaController {
    private static final Logger log = LoggerFactory.getLogger(AuditoriaController.class);
    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @PostMapping
    public ResponseEntity<Void> receberAuditoria(@RequestBody AuditoriaEstoqueDTO auditoriaDTO) {
        log.info("Requisição POST de auditoria recebida: {}", auditoriaDTO.getAcao());
        auditoriaService.salvarAuditoria(auditoriaDTO);
        return ResponseEntity.ok().build();
    }
}

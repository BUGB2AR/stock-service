package com.dev.jarmison.stock_service.service;

import com.dev.jarmison.stock_service.dto.AuditoriaEstoqueDTO;
import com.dev.jarmison.stock_service.model.AuditoriaLog;
import com.dev.jarmison.stock_service.repository.AuditoriaLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {
    private static final Logger log = LoggerFactory.getLogger(AuditoriaService.class);
    private final AuditoriaLogRepository auditoriaLogRepository;

    public AuditoriaService(AuditoriaLogRepository auditoriaLogRepository) {
        this.auditoriaLogRepository = auditoriaLogRepository;
    }

    public void salvarAuditoria(AuditoriaEstoqueDTO auditoriaDTO) {
        log.info("Recebendo auditoria para salvar no banco de dados: {}", auditoriaDTO.getAcao());
        AuditoriaLog auditoriaLog = new AuditoriaLog();
        auditoriaLog.setProdutoId(auditoriaDTO.getProdutoId());
        auditoriaLog.setAcao(auditoriaDTO.getAcao());
        auditoriaLog.setDetalhes(auditoriaDTO.getDetalhes());
        auditoriaLog.setDataAcao(auditoriaDTO.getDataAcao());
        auditoriaLogRepository.save(auditoriaLog);
        log.info("Log de auditoria salvo com sucesso.");
    }
}
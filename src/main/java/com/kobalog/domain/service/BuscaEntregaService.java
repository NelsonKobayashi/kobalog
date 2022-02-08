package com.kobalog.domain.service;

import com.kobalog.domain.exception.EntidadeNaoEncontradaException;
import com.kobalog.domain.exception.NegocioException;
import com.kobalog.domain.model.Entrega;
import com.kobalog.domain.repository.EntregaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BuscaEntregaService {

    private EntregaRepository entregaRepository;

    public Entrega buscar(Long entregaId) {
        return entregaRepository.findById(entregaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Entrega não encontrada"));
    }
}

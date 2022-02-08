package com.kobalog.domain.service;


import com.kobalog.domain.exception.NegocioException;
import com.kobalog.domain.model.Cliente;
import com.kobalog.domain.model.Entrega;
import com.kobalog.domain.model.StatusEntrega;
import com.kobalog.domain.repository.ClienteRepository;
import com.kobalog.domain.repository.EntregaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Service
public class SolicitacaoEntregaService {

    private CatalogoClienteService catalogoClienteService;
    private EntregaRepository entregaRepository;

    @Transactional
    public Entrega solicitar(Entrega entrega) {
        Cliente cliente = catalogoClienteService.buscar(entrega.getCliente().getId());

        entrega.setCliente(cliente);
        entrega.setStatus(StatusEntrega.PENDENTE);
        entrega.setDataPedido(OffsetDateTime.now());


        return entregaRepository.save(entrega);

    }
}

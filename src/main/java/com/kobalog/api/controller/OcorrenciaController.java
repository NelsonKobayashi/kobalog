package com.kobalog.api.controller;


import com.kobalog.api.assembler.OcorrenciaAssembler;
import com.kobalog.api.model.OcorrenciaModel;
import com.kobalog.api.model.input.OcorrenciaInput;
import com.kobalog.domain.model.Entrega;
import com.kobalog.domain.model.Ocorrencia;
import com.kobalog.domain.service.BuscaEntregaService;
import com.kobalog.domain.service.RegistroOcorrenciaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas/{entregaId}/ocorrencias")
public class OcorrenciaController {

    private BuscaEntregaService buscaEntregaService;
    private RegistroOcorrenciaService registroOcorrenciaService;
    private OcorrenciaAssembler ocorrenciaAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OcorrenciaModel registrar(@PathVariable Long entregaId,
                                      @Valid @RequestBody OcorrenciaInput ocorrenciaInput) {
        Ocorrencia ocorrenciaRegistrada = registroOcorrenciaService
                                            .registrar(entregaId, ocorrenciaInput.getDescricao());

        return ocorrenciaAssembler.toModel(ocorrenciaRegistrada);
    }

    @GetMapping
    public List<OcorrenciaModel> listar(@PathVariable long entregaId) {
        Entrega entrega = buscaEntregaService.buscar(entregaId);
        return ocorrenciaAssembler.toCollectionModel(entrega.getOcorrencias());
    }

}

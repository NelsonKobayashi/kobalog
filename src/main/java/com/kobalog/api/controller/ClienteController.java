package com.kobalog.api.controller;

import com.kobalog.domain.model.Cliente;
import com.kobalog.domain.repository.ClienteRepository;
import com.kobalog.domain.service.CatalogoClienteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;
    private CatalogoClienteService catalogoClienteService;

    //método para listar todos os clientes
    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    //método para buscar cliente pelo ID
    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {

        //maneira 1 de resolver.
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        if (cliente.isPresent()) { return ResponseEntity.ok(cliente.get()); }
        return ResponseEntity.notFound().build();

        //maneira 2 de resolver (com Lambda Expression)
        //return clienteRepository.findById(clienteId)
        //        .map(cliente -> ResponseEntity.ok(cliente))
        //        .orElse(ResponseEntity.notFound().build());

        //maneira 3 de resolver (com Metod Reference)
        //return clienteRepository.findById(clienteId)
        //        .map(ResponseEntity::ok)
        //        .orElse(ResponseEntity.notFound().build());
    }

    //método para adicionar cliente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // anotação para alterar o retorno da resposta padrão "200", para "201".
    public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
        return catalogoClienteService.salvar(cliente);
    }

    //método para atualizar cliente
    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId,
                                             @Valid @RequestBody Cliente cliente) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }
        cliente.setId(clienteId);
        cliente = catalogoClienteService.salvar(cliente);
        return ResponseEntity.ok(cliente);
    }

    //método deletar
    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }

        catalogoClienteService.excluir(clienteId);
        return ResponseEntity.noContent().build();
    }
}

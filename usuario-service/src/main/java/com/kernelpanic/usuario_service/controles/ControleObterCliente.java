package com.kernelpanic.usuario_service.controles;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kernelpanic.usuario_service.dtos.ClienteDTO.ClienteExibirDTO;
import com.kernelpanic.usuario_service.servicos.ClienteServico;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ControleObterCliente {

    private final ClienteServico clienteServico;

    @GetMapping
    public ResponseEntity<List<ClienteExibirDTO>> listarAtivos() {
        return ResponseEntity.ok(clienteServico.listarAtivos());
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ClienteExibirDTO>> listarTodos() {
        return ResponseEntity.ok(clienteServico.listarTodos());
    }

    @GetMapping("/projetos")
    public ResponseEntity<List<ClienteExibirDTO>> listarPorProjetos(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(clienteServico.listarPorProjetoIds(ids));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteExibirDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteServico.buscarPorId(id));
    }
}

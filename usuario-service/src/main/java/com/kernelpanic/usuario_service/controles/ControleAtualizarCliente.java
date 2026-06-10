package com.kernelpanic.usuario_service.controles;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kernelpanic.usuario_service.dtos.ClienteDTO.ClienteAtualizarDTO;
import com.kernelpanic.usuario_service.dtos.ClienteDTO.ClienteExibirDTO;
import com.kernelpanic.usuario_service.servicos.ClienteServico;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ControleAtualizarCliente {

    private final ClienteServico clienteServico;

    @PutMapping("/{id}")
    public ResponseEntity<ClienteExibirDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ClienteAtualizarDTO dto) {
        return ResponseEntity.ok(clienteServico.atualizar(id, dto));
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        clienteServico.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
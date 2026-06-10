package com.kernelpanic.usuario_service.controles;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kernelpanic.usuario_service.dtos.ClienteDTO.ClienteExibirDTO;
import com.kernelpanic.usuario_service.dtos.ClienteDTO.ClienteVincularProjetosDTO;
import com.kernelpanic.usuario_service.servicos.ClienteServico;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ControleClienteProjeto {

    private final ClienteServico clienteServico;

    @PostMapping("/{id}/projetos")
    public ResponseEntity<ClienteExibirDTO> vincularProjetos(
            @PathVariable Long id,
            @RequestBody @Valid ClienteVincularProjetosDTO dto) {
        return ResponseEntity.ok(clienteServico.vincularProjetos(id, dto.projetoIds()));
    }

    @DeleteMapping("/{clienteId}/projetos/{projetoId}")
    public ResponseEntity<Void> desvincularProjeto(
            @PathVariable Long clienteId,
            @PathVariable Long projetoId) {
        clienteServico.desvincularProjeto(clienteId, projetoId);
        return ResponseEntity.noContent().build();
    }
}
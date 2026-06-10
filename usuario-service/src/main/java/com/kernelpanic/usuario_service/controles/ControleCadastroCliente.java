package com.kernelpanic.usuario_service.controles;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kernelpanic.usuario_service.dtos.ClienteDTO.ClienteCadastroDTO;
import com.kernelpanic.usuario_service.dtos.ClienteDTO.ClienteExibirDTO;
import com.kernelpanic.usuario_service.servicos.ClienteServico;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ControleCadastroCliente {

    private final ClienteServico clienteServico;

    @PostMapping
    public ResponseEntity<ClienteExibirDTO> cadastrar(@RequestBody @Valid ClienteCadastroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteServico.cadastrar(dto));
    }
}
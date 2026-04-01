package com.kernelpanic.usuario_service.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kernelpanic.usuario_service.dtos.UsuarioCadastroDTO;
import com.kernelpanic.usuario_service.servicos.UsuarioServico;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class ControleCadastroUsuario {
    
    @Autowired
    private UsuarioServico servico;

    @PostMapping("/cadastro")
    public void cadastrarUsuario(@Valid @RequestBody UsuarioCadastroDTO dto){
        servico.cadastrarViaDTO(dto);
    }
}

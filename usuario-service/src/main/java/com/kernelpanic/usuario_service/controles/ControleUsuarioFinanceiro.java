package com.kernelpanic.usuario_service.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kernelpanic.usuario_service.dtos.UsuarioFinanceiroDTO;
import com.kernelpanic.usuario_service.servicos.UsuarioServico;

@RestController
@RequestMapping("/usuario")
public class ControleUsuarioFinanceiro {

    @Autowired
    private UsuarioServico servico;

    @GetMapping("/financeiro")
    public List<UsuarioFinanceiroDTO> obterFinanceiroPorIds(@RequestParam List<Long> ids) {
        return servico.obterFinanceiroPorIds(ids);
    }
}

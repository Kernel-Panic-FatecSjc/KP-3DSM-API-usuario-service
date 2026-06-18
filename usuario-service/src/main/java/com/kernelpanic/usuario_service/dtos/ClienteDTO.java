package com.kernelpanic.usuario_service.dtos;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ClienteDTO {

    public record ClienteCadastroDTO(

        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 150)
        String nome,

        @NotBlank(message = "CNPJ é obrigatório")
        @Size(max = 18, message = "CNPJ inválido")
        String cnpj,

        @Email(message = "E-mail inválido")
        @Size(max = 150)
        String email,

        @Size(max = 20)
        String telefone,

        @Size(max = 200)
        String observacao,
        List<Long> projetoIds
    ) {}

    public record ClienteExibirDTO(
        Long id,
        String nome,
        String cnpj,
        String email,
        String telefone,
        String observacao,
        Boolean ativo,
        List<Long> projetoIds
    ) {}
    public record ClienteAtualizarDTO(

        @Size(max = 150)
        String nome,

        @Size(max = 18)
        String cnpj,

        @Email
        @Size(max = 150)
        String email,

        @Size(max = 20)
        String telefone,

        @Size(max = 200)
        String observacao,

        Boolean ativo
    ) {}

    public record ClienteVincularProjetosDTO(
        @NotNull
        List<Long> projetoIds
    ) {}

    public record ClienteFinanceiroDTO(
        Long clienteId,
        String nome,
        java.math.BigDecimal valorContratado,
        java.math.BigDecimal custoReal,
        java.math.BigDecimal lucro
    ) {}
}


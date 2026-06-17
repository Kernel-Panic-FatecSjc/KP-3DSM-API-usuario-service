package com.kernelpanic.usuario_service.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.br.CPF;

import com.kernelpanic.usuario_service.enums.TipoContratacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioExibirDTO {

    @NotNull(message = "O ID do usuario é obrigatório")
    private Long id;

    private Long gerenteId;

    @NotBlank(message = "O nome do usuario é obrigatório")
    private String nome;

    @CPF(message = "CPF inválido")
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @NotBlank(message = "O cargo é obrigatório")
    private String cargo;

    @NotBlank(message = "O email não pode ser vazio")
    private String email;

    @NotBlank(message = "O salário não pode ser nulo")
    private String salario;

    private TipoContratacao tipoContratacao;

    private BigDecimal valorMensal;

    private BigDecimal valorHora;

    private String tipoContrato;

    @NotNull(message = "A data de criação é obrigatória")
    private LocalDateTime dataCriacao;

    private Boolean ativo;
}

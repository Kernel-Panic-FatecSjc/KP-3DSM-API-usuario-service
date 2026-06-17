package com.kernelpanic.usuario_service.dtos;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.br.CPF;

import com.kernelpanic.usuario_service.enums.TipoContratacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioAtualizarDTO {

    @NotNull(message = "O ID do usuario é obrigatório")
    private Long id;

    private Long gerenteId;

    @Size(max = 100, message = "O nome não pode exceder 100 caracteres")
    private String nome;

    @CPF(message = "CPF inválido")
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @Size(max = 30, message = "O cargo não pode passar de 30 caracteres")
    private String cargo;

    @Email(message = "Email inválido")
    @Size(max = 150, message = "O email não pode ultrapassar 150 caracteres")
    private String email;

    @NotBlank(message = "O salário é obrigatório")
    @Size(max = 20, message = "O salário não pode ultrapassar 20 caracteres")
    private String salario;

    private TipoContratacao tipoContratacao;

    @PositiveOrZero(message = "O valor mensal nao pode ser negativo")
    private BigDecimal valorMensal;

    @PositiveOrZero(message = "O valor hora nao pode ser negativo")
    private BigDecimal valorHora;

    @Size(max = 255, message = "A senha não pode ultrapassar 255 caracteres")
    private String senha;

    @Size(max = 50, message = "O tipo de contrato não pode ultrapassar 50 caracteres")
    private String tipoContrato;

    private Boolean ativo;
}

package com.kernelpanic.usuario_service.validacoes;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CnpjValidator implements ConstraintValidator<CnpjValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        String cnpj = value.replaceAll("\\D", "");

        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        return digitoValido(cnpj, 12, 5, cnpj.charAt(12))
            && digitoValido(cnpj, 13, 6, cnpj.charAt(13));
    }

    private boolean digitoValido(String cnpj, int tamanho, int pesoInicial, char digitoEsperado) {
        int soma = 0;
        int peso = pesoInicial;

        for (int i = 0; i < tamanho; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso--;
            if (peso < 2) {
                peso = 9;
            }
        }

        int resto = soma % 11;
        int digito = resto < 2 ? 0 : 11 - resto;
        return digito == Character.getNumericValue(digitoEsperado);
    }
}
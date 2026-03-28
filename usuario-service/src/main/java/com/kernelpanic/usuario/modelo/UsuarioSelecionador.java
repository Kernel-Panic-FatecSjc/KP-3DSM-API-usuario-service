package com.kernelpanic.usuario.modelo;

import java.util.List;

import com.kernelpanic.usuario.entidade.Usuario;

import org.springframework.stereotype.Component;


@Component
public class UsuarioSelecionador {
	public Usuario selecionar(List<Usuario> usuarios, long id) {
		Usuario selecionado = null;
		for (Usuario usuario : usuarios) {
			if (usuario.getId() == id) {
				selecionado = usuario;
			}
		}
		return selecionado;
	}
}

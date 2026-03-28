package com.kernelpanic.usuario.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kernelpanic.usuario.entidade.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

}
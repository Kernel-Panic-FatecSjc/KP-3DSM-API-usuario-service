package com.kernelpanic.usuario_service.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kernelpanic.usuario_service.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
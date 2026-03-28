package com.kernelpanic.usuario.servico;

import org.springframework.stereotype.Service;

import com.kernelpanic.usuario.entidade.Usuario;
import com.kernelpanic.usuario.repositorio.UsuarioRepositorio;

@Service
public class UserService {

    private final UsuarioRepositorio repository;

    public UserService(UsuarioRepositorio repository) {
        this.repository = repository;
    }

    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }
}
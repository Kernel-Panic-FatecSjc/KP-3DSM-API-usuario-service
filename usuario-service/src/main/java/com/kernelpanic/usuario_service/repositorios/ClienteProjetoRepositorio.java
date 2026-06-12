package com.kernelpanic.usuario_service.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kernelpanic.usuario_service.entidades.ClienteProjeto;

public interface ClienteProjetoRepositorio extends JpaRepository<ClienteProjeto, Long> {

    List<ClienteProjeto> findByClienteId(Long clienteId);

    void deleteByClienteIdAndProjetoId(Long clienteId, Long projetoId);

    boolean existsByClienteIdAndProjetoId(Long clienteId, Long projetoId);

    boolean existsByProjetoIdAndCliente_IdNot(Long projetoId, Long clienteId);
}

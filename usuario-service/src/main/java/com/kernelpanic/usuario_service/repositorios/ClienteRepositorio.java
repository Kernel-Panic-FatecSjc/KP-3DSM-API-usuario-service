package com.kernelpanic.usuario_service.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kernelpanic.usuario_service.entidades.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCnpj(String cnpj);
    List<Cliente> findByAtivoTrue();
    List<Cliente> findDistinctByProjetosProjetoIdIn(List<Long> projetoIds);
    boolean existsByCnpj(String cnpj);
}

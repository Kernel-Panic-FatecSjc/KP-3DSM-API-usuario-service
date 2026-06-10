package com.kernelpanic.usuario_service.servicos;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kernelpanic.usuario_service.dtos.ClienteDTO.ClienteAtualizarDTO;
import com.kernelpanic.usuario_service.dtos.ClienteDTO.ClienteCadastroDTO;
import com.kernelpanic.usuario_service.dtos.ClienteDTO.ClienteExibirDTO;
import com.kernelpanic.usuario_service.entidades.Cliente;
import com.kernelpanic.usuario_service.entidades.ClienteProjeto;
import com.kernelpanic.usuario_service.excecoes.EntidadeNaoEncontradaException;
import com.kernelpanic.usuario_service.repositorios.ClienteProjetoRepositorio;
import com.kernelpanic.usuario_service.repositorios.ClienteRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServico {

    private final ClienteRepositorio clienteRepositorio;
    private final ClienteProjetoRepositorio clienteProjetoRepositorio;

    @Transactional
    public ClienteExibirDTO cadastrar(ClienteCadastroDTO dto) {
        if (clienteRepositorio.existsByCnpj(dto.cnpj())) {
            throw new IllegalArgumentException("Já existe um cliente com esse CNPJ.");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setCnpj(dto.cnpj());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setObservacao(dto.observacao());
        cliente.setAtivo(true);

        clienteRepositorio.save(cliente);

        if (dto.projetoIds() != null) {
            for (Long projetoId : dto.projetoIds()) {
                vincularProjeto(cliente, projetoId);
            }
        }

        return toDTO(cliente);
    }

    public List<ClienteExibirDTO> listarAtivos() {
        return clienteRepositorio.findByAtivoTrue()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ClienteExibirDTO> listarTodos() {
        return clienteRepositorio.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ClienteExibirDTO buscarPorId(Long id) {
        Cliente cliente = buscarEntidade(id);
        return toDTO(cliente);
    }

    @Transactional
    public ClienteExibirDTO atualizar(Long id, ClienteAtualizarDTO dto) {
        Cliente cliente = buscarEntidade(id);

        if (dto.nome() != null) cliente.setNome(dto.nome());
        if (dto.cnpj() != null) cliente.setCnpj(dto.cnpj());
        if (dto.email() != null) cliente.setEmail(dto.email());
        if (dto.telefone() != null) cliente.setTelefone(dto.telefone());
        if (dto.observacao() != null) cliente.setObservacao(dto.observacao());
        if (dto.ativo() != null) cliente.setAtivo(dto.ativo());

        return toDTO(cliente);
    }

    @Transactional
    public void desativar(Long id) {
        Cliente cliente = buscarEntidade(id);
        cliente.setAtivo(false);
    }

    @Transactional
    public ClienteExibirDTO vincularProjetos(Long clienteId, List<Long> projetoIds) {
        Cliente cliente = buscarEntidade(clienteId);

        for (Long projetoId : projetoIds) {
            if (!clienteProjetoRepositorio.existsByClienteIdAndProjetoId(clienteId, projetoId)) {
                vincularProjeto(cliente, projetoId);
            }
        }

        return toDTO(cliente);
    }

    @Transactional
    public void desvincularProjeto(Long clienteId, Long projetoId) {
        buscarEntidade(clienteId);
        clienteProjetoRepositorio.deleteByClienteIdAndProjetoId(clienteId, projetoId);
    }

    private void vincularProjeto(Cliente cliente, Long projetoId) {
        ClienteProjeto vinculo = new ClienteProjeto();
        vinculo.setCliente(cliente);
        vinculo.setProjetoId(projetoId);
        clienteProjetoRepositorio.save(vinculo);
    }

    private Cliente buscarEntidade(Long id) {
        return clienteRepositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                "Cliente não encontrado",
                "Nenhum cliente encontrado com o id: " + id
                ));
    }

    private ClienteExibirDTO toDTO(Cliente c) {
        List<Long> projetoIds = clienteProjetoRepositorio
                .findByClienteId(c.getId())
                .stream()
                .map(ClienteProjeto::getProjetoId)
                .toList();

        return new ClienteExibirDTO(
                c.getId(),
                c.getNome(),
                c.getCnpj(),
                c.getEmail(),
                c.getTelefone(),
                c.getObservacao(),
                c.getAtivo(),
                projetoIds
        );
    }
}

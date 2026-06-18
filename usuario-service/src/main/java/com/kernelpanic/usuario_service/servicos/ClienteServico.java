package com.kernelpanic.usuario_service.servicos;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.kernelpanic.usuario_service.dtos.ClienteDTO.ClienteAtualizarDTO;
import com.kernelpanic.usuario_service.dtos.ClienteDTO.ClienteCadastroDTO;
import com.kernelpanic.usuario_service.dtos.ClienteDTO.ClienteExibirDTO;
import com.kernelpanic.usuario_service.dtos.ClienteDTO.ClienteFinanceiroDTO;
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
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${projeto.service.url:http://spring-api-container:8082}")
    private String projetoServiceUrl;

    @Value("${internal.api-key:}")
    private String internalApiKey;

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

    public List<ClienteFinanceiroDTO> listarFinanceiroPorCliente() {
        return clienteRepositorio.findAll()
                .stream()
                .sorted(Comparator.comparing(Cliente::getId, Comparator.nullsLast(Long::compareTo)))
                .map(this::toFinanceiroDTO)
                .toList();
    }

    public List<ClienteExibirDTO> listarPorProjetoIds(List<Long> projetoIds) {
        if (projetoIds == null || projetoIds.isEmpty()) {
            return List.of();
        }

        return clienteRepositorio.findDistinctByProjetosProjetoIdIn(projetoIds)
                .stream()
                .sorted(Comparator.comparing(Cliente::getId, Comparator.nullsLast(Long::compareTo)))
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

        if (dto.cnpj() != null) {
            if (!cliente.getCnpj().equals(dto.cnpj()) && clienteRepositorio.existsByCnpj(dto.cnpj())) {
                throw new IllegalArgumentException("Já existe um cliente com esse CNPJ.");
            }
            cliente.setCnpj(dto.cnpj());
        }

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
        if (clienteProjetoRepositorio.existsByProjetoIdAndCliente_IdNot(projetoId, cliente.getId())) {
            throw new IllegalArgumentException("Projeto " + projetoId + " ja esta vinculado a outro cliente.");
        }

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

    private ClienteFinanceiroDTO toFinanceiroDTO(Cliente cliente) {
        BigDecimal valorContratado = BigDecimal.ZERO;
        BigDecimal custoReal = BigDecimal.ZERO;
        BigDecimal lucro = BigDecimal.ZERO;

        List<Long> projetoIds = clienteProjetoRepositorio
                .findByClienteId(cliente.getId())
                .stream()
                .map(ClienteProjeto::getProjetoId)
                .toList();

        for (Long projetoId : projetoIds) {
            Map<String, Object> projeto = buscarProjeto(projetoId);

            BigDecimal valorContratadoProjeto = obterValor(projeto, "valorContratado", "valor_contratado");
            BigDecimal custoRealProjeto = obterValor(projeto, "custoReal", "custo_real");
            BigDecimal lucroProjeto = obterValor(projeto, "lucro", "lucroProjeto", "lucro_projeto");

            if (!possuiValor(projeto, "lucro", "lucroProjeto", "lucro_projeto")) {
                lucroProjeto = valorContratadoProjeto.subtract(custoRealProjeto);
            }

            valorContratado = valorContratado.add(valorContratadoProjeto);
            custoReal = custoReal.add(custoRealProjeto);
            lucro = lucro.add(lucroProjeto);
        }

        return new ClienteFinanceiroDTO(
                cliente.getId(),
                cliente.getNome(),
                valorContratado,
                custoReal,
                lucro
        );
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> buscarProjeto(Long projetoId) {
        String url = projetoServiceUrl + "/projeto/" + projetoId;

        HttpHeaders headers = new HttpHeaders();
        if (internalApiKey != null && !internalApiKey.isBlank()) {
            headers.set("X-Internal-Api-Key", internalApiKey);
        }

        Object body = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Object.class
        ).getBody();

        if (body instanceof Map<?, ?> map) {
            return (Map<String, Object>) map;
        }

        return Map.of();
    }

    private BigDecimal obterValor(Map<String, Object> dados, String... chaves) {
        for (String chave : chaves) {
            Object valor = dados.get(chave);

            if (valor instanceof Number number) {
                return new BigDecimal(number.toString());
            }

            if (valor instanceof String texto && !texto.isBlank()) {
                return new BigDecimal(texto);
            }
        }

        return BigDecimal.ZERO;
    }

    private boolean possuiValor(Map<String, Object> dados, String... chaves) {
        for (String chave : chaves) {
            if (dados.get(chave) != null) {
                return true;
            }
        }

        return false;
    }
}    


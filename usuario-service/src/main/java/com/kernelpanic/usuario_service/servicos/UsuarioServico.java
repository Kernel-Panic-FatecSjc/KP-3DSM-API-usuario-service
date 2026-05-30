package com.kernelpanic.usuario_service.servicos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kernelpanic.usuario_service.dtos.UsuarioAtualizarDTO;
import com.kernelpanic.usuario_service.dtos.UsuarioCadastroDTO;
import com.kernelpanic.usuario_service.dtos.UsuarioExibirDTO;
import com.kernelpanic.usuario_service.dtos.UsuarioFinanceiroDTO;
import com.kernelpanic.usuario_service.entidades.Usuario;
import com.kernelpanic.usuario_service.excecoes.EntidadeNaoEncontradaException;
import com.kernelpanic.usuario_service.repositorios.UsuarioRepositorio;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.client.RestTemplate;

@Service
public class UsuarioServico {

    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<UsuarioExibirDTO> obterTodos() {
        List<Usuario> usuarios = repositorio.findAll();
        return usuarios.stream()
                .map(this::converterParaExibirDTO)
                .collect(Collectors.toList());
    }

    public UsuarioExibirDTO obterPorId(Long id) {
        Usuario usuario = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                "Usuário não encontrado",
                "Não foi possível localizar um usuário com o ID: " + id
        ));

        return converterParaExibirDTO(usuario);
    }

    public void cadastrar(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        repositorio.save(usuario);
        try {
            Map<String, Object> authPayload = new HashMap<>();
            authPayload.put("nome", usuario.getNome());
            authPayload.put("email", usuario.getEmail());
            authPayload.put("senha", usuario.getSenha());
            authPayload.put("cargo", usuario.getCargo());
            authPayload.put("salario", usuario.getSalario());
            restTemplate.postForObject("http://auth-service-app:8081/auth/cadastro", authPayload, Void.class);
        } catch (Exception e) {
            System.err.println("ERRO AO CADASTRAR NO AUTH-SERVICE: " + e.getMessage());
        }
    }

    public void atualizar(Usuario atualizacao) {
        Usuario usuario = repositorio.findById(atualizacao.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                "Atualização impossível",
                "O usuário de ID " + atualizacao.getId() + " não foi encontrado."
        ));

        if (atualizacao.getNome() != null) {
            usuario.setNome(atualizacao.getNome());
        }
        if (atualizacao.getCargo() != null) {
            usuario.setCargo(atualizacao.getCargo());
        }
        if (atualizacao.getEmail() != null) {
            usuario.setEmail(atualizacao.getEmail());
        }
        if (atualizacao.getSalario() != null) {
            usuario.setSalario(atualizacao.getSalario());
        }
        if (atualizacao.getTipoContratacao() != null) {
            usuario.setTipoContratacao(atualizacao.getTipoContratacao());
        }
        if (atualizacao.getValorMensal() != null) {
            usuario.setValorMensal(atualizacao.getValorMensal());
        }
        if (atualizacao.getValorHora() != null) {
            usuario.setValorHora(atualizacao.getValorHora());
        }
        

        if (atualizacao.getGerente() != null && atualizacao.getGerente().getId() != null) {
            Usuario gerente = new Usuario();
            gerente.setId(atualizacao.getGerente().getId());
            usuario.setGerente(gerente);
        } else {
            usuario.setGerente(null);
        }

        if (atualizacao.getSenha() != null && !atualizacao.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(atualizacao.getSenha()));
        }

        repositorio.save(usuario);
    }

    public void deletarPorId(Long id) {
        Usuario usuario = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                "Exclusão negada",
                "Não foi possível localizar o usuário de ID " + id
        ));

        repositorio.delete(usuario);
    }

    private UsuarioExibirDTO converterParaExibirDTO(Usuario usuario) {
        UsuarioExibirDTO dto = new UsuarioExibirDTO();

        dto.setId(usuario.getId());

        if (usuario.getGerente() != null) {
            dto.setGerenteId(usuario.getGerente().getId());
        }

        dto.setNome(usuario.getNome());
        dto.setCargo(usuario.getCargo());
        dto.setEmail(usuario.getEmail());
        dto.setDataCriacao(usuario.getDataCriacao());
        dto.setSalario(usuario.getSalario());
        dto.setTipoContratacao(usuario.getTipoContratacao());
        dto.setValorMensal(usuario.getValorMensal());
        dto.setValorHora(usuario.getValorHora());

        return dto;
    }

    public List<UsuarioFinanceiroDTO> obterFinanceiroPorIds(List<Long> ids) {
        return repositorio.findAllById(ids).stream()
                .map(this::converterParaFinanceiroDTO)
                .collect(Collectors.toList());
    }

    private UsuarioFinanceiroDTO converterParaFinanceiroDTO(Usuario usuario) {
        UsuarioFinanceiroDTO dto = new UsuarioFinanceiroDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setTipoContratacao(usuario.getTipoContratacao());
        dto.setValorMensal(usuario.getValorMensal());
        dto.setValorHora(usuario.getValorHora());
        return dto;
    }

    public void cadastrarViaDTO(UsuarioCadastroDTO dto) {
        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setCargo(dto.getCargo());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setSalario(dto.getSalario());
        usuario.setTipoContratacao(dto.getTipoContratacao());
        usuario.setValorMensal(dto.getValorMensal());
        usuario.setValorHora(dto.getValorHora());

        if (dto.getGerenteId() != null) {
            Usuario gerente = new Usuario();
            gerente.setId(dto.getGerenteId());
            usuario.setGerente(gerente);
        }

        this.cadastrar(usuario);
    }

    public void atualizarViaDTO(Long id, UsuarioAtualizarDTO dto) {
        Usuario usuario = new Usuario();

        usuario.setId(id);
        usuario.setNome(dto.getNome());
        usuario.setCargo(dto.getCargo());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setSalario(dto.getSalario());
        usuario.setTipoContratacao(dto.getTipoContratacao());
        usuario.setValorMensal(dto.getValorMensal());
        usuario.setValorHora(dto.getValorHora());
        


        if (dto.getGerenteId() != null) {
            Usuario gerente = new Usuario();
            gerente.setId(dto.getGerenteId());
            usuario.setGerente(gerente);
        }

        this.atualizar(usuario);
    }
}

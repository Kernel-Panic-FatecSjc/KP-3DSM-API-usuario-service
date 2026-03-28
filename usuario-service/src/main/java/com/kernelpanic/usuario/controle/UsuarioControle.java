package com.kernelpanic.usuario.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.kernelpanic.usuario.entidade.Usuario;
import com.kernelpanic.usuario.modelo.UsuarioAtualizador;
import com.kernelpanic.usuario.modelo.UsuarioSelecionador;
import com.kernelpanic.usuario.repositorio.UsuarioRepositorio;

@RestController
@RequestMapping("/usuario")
public class UsuarioControle {

    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    private UsuarioSelecionador selecionador;

    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @GetMapping("/{id}")
    public Usuario obterUsuario(@PathVariable long id) {
        List<Usuario> usuarios = repositorio.findAll();
        return selecionador.selecionar(usuarios, id);
    }

    @GetMapping("/todos")
    public List<Usuario> obterUsuarios() {
        List<Usuario> usuarios = repositorio.findAll();
        return usuarios;
    }

    @PostMapping("/cadastro")
    public void cadastrarUsuario(@RequestBody Usuario usuario) {
        String senhaCriptografada = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        repositorio.save(usuario);
    }

    @PutMapping("/atualizar")
    public void atualizarUsuario(@RequestBody Usuario atualizacao) {
        Usuario usuario = repositorio.getById(atualizacao.getId());

        UsuarioAtualizador atualizador = new UsuarioAtualizador();
        atualizador.atualizar(usuario, atualizacao);

        repositorio.save(usuario);
    }

    @DeleteMapping("/excluir")
    public void excluirUsuario(@RequestBody Usuario exclusao) {
        Usuario usuario = repositorio.getById(exclusao.getId());
        repositorio.delete(usuario);
    }
}
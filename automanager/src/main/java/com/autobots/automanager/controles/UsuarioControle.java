package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Usuario;

import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.servicos.atualizador.UsuarioAtualizador;
import com.autobots.automanager.servicos.hateoasdor.AdicionadorLinkUsuario;
import com.autobots.automanager.servicos.selecionador.UsuarioSelecionador;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControle {
    @Autowired
    private UsuarioRepositorio repositorio;
    @Autowired
    private UsuarioSelecionador selecionador;
    @Autowired
    private AdicionadorLinkUsuario adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterUsuario(@PathVariable long id) {
        List<Usuario> usuarios = repositorio.findAll();
        Usuario usuario = selecionador.selecionar(usuarios, id);
        if (usuario == null) {
            ResponseEntity<Usuario> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
        } else {
            adicionadorLink.adicionarLink(usuarios);
			ResponseEntity<Usuario> resposta = new ResponseEntity<Usuario>(usuario, HttpStatus.FOUND);
			return resposta;
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> obterUsuarios() {
        List<Usuario> usuarios = repositorio.findAll();
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(usuarios);
			ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(usuarios, HttpStatus.FOUND);
			return resposta;
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (usuario.getId() == null) {
            repositorio.save(usuario);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Optional<Usuario> optionalUsuario = repositorio.findById(atualizacao.getId());
        Usuario usuario = new Usuario();
        if (optionalUsuario.isPresent()) {
            usuario = optionalUsuario.get();
        } else {
            usuario = null;
        }
        if (usuario != null) {
            UsuarioAtualizador atualizador = new UsuarioAtualizador();
            atualizador.atualizar(usuario, atualizacao);
            repositorio.save(usuario);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirUsuario(@RequestBody Usuario exclusao) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Optional<Usuario> optionalUsuario = repositorio.findById(exclusao.getId());
        Usuario usuario = new Usuario();
        if (optionalUsuario.isPresent()) {
            usuario = optionalUsuario.get();
        } else {
            usuario = null;
        }
        if (usuario != null) {
            repositorio.delete(usuario);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}

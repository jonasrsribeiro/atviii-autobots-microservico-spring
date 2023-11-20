package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Mercadoria;

import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.servicos.atualizador.MercadoriaAtualizador;
import com.autobots.automanager.servicos.hateoasdor.AdicionadorLinkMercadoria;
import com.autobots.automanager.servicos.selecionador.MercadoriaSelecionador;

@RestController
@RequestMapping("/mercadorias")
public class MercadoriaControle {
    @Autowired
    private MercadoriaRepositorio repositorio;
    @Autowired
    private MercadoriaSelecionador selecionador;
    @Autowired
    private AdicionadorLinkMercadoria adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable long id) {
        List<Mercadoria> mercadorias = repositorio.findAll();
        Mercadoria mercadoria = selecionador.selecionar(mercadorias, id);
        if (mercadoria == null) {
            ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
        } else {
            adicionadorLink.adicionarLink(mercadorias);
			ResponseEntity<Mercadoria> resposta = new ResponseEntity<Mercadoria>(mercadoria, HttpStatus.FOUND);
			return resposta;
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Mercadoria>> obterMercadorias() {
        List<Mercadoria> mercadorias = repositorio.findAll();
        if (mercadorias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(mercadorias);
			ResponseEntity<List<Mercadoria>> resposta = new ResponseEntity<>(mercadorias, HttpStatus.FOUND);
			return resposta;
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarMercadoria(@RequestBody Mercadoria mercadoria) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (mercadoria.getId() == null) {
            repositorio.save(mercadoria);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarMercadoria(@RequestBody Mercadoria atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Optional<Mercadoria> optionalMercadoria = repositorio.findById(atualizacao.getId());
        Mercadoria mercadoria = new Mercadoria();
        if (optionalMercadoria.isPresent()) {
            mercadoria = optionalMercadoria.get();
        } else {
            mercadoria = null;
        }
        if (mercadoria != null) {
            MercadoriaAtualizador atualizador = new MercadoriaAtualizador();
            atualizador.atualizar(mercadoria, atualizacao);
            repositorio.save(mercadoria);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirMercadoria(@RequestBody Mercadoria exclusao) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Optional<Mercadoria> optionalMercadoria = repositorio.findById(exclusao.getId());
        Mercadoria mercadoria = new Mercadoria();
        if (optionalMercadoria.isPresent()) {
            mercadoria = optionalMercadoria.get();
        } else {
            mercadoria = null;
        }
        if (mercadoria != null) {
            repositorio.delete(mercadoria);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}

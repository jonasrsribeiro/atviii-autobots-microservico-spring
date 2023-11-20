package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Servico;

import com.autobots.automanager.repositorios.ServicoRepositorio;
import com.autobots.automanager.servicos.atualizador.ServicoAtualizador;
import com.autobots.automanager.servicos.hateoasdor.AdicionadorLinkServico;
import com.autobots.automanager.servicos.selecionador.ServicoSelecionador;

@RestController
@RequestMapping("/servicos")
public class ServicoControle {
    @Autowired
    private ServicoRepositorio repositorio;
    @Autowired
    private ServicoSelecionador selecionador;
    @Autowired
    private AdicionadorLinkServico adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Servico> obterServico(@PathVariable long id) {
        List<Servico> servicos = repositorio.findAll();
        Servico servico = selecionador.selecionar(servicos, id);
        if (servico == null) {
            ResponseEntity<Servico> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
        } else {
            adicionadorLink.adicionarLink(servicos);
			ResponseEntity<Servico> resposta = new ResponseEntity<Servico>(servico, HttpStatus.FOUND);
			return resposta;
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Servico>> obterServicos() {
        List<Servico> servicos = repositorio.findAll();
        if (servicos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(servicos);
			ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(servicos, HttpStatus.FOUND);
			return resposta;
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarServico(@RequestBody Servico servico) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (servico.getId() == null) {
            repositorio.save(servico);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarServico(@RequestBody Servico atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Optional<Servico> optionalServico = repositorio.findById(atualizacao.getId());
        Servico servico = new Servico();
        if (optionalServico.isPresent()) {
            servico = optionalServico.get();
        } else {
            servico = null;
        }
        if (servico != null) {
            ServicoAtualizador atualizador = new ServicoAtualizador();
            atualizador.atualizar(servico, atualizacao);
            repositorio.save(servico);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirServico(@RequestBody Servico exclusao) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Optional<Servico> optionalServico = repositorio.findById(exclusao.getId());
        Servico servico = new Servico();
        if (optionalServico.isPresent()) {
            servico = optionalServico.get();
        } else {
            servico = null;
        }
        if (servico != null) {
            repositorio.delete(servico);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}

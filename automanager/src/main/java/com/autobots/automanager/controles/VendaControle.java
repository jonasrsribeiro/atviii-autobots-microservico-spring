package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Venda;

import com.autobots.automanager.repositorios.VendaRepositorio;
import com.autobots.automanager.servicos.atualizador.VendaAtualizador;
import com.autobots.automanager.servicos.hateoasdor.AdicionadorLinkVenda;
import com.autobots.automanager.servicos.selecionador.VendaSelecionador;

@RestController
@RequestMapping("/vendas")
public class VendaControle {
    @Autowired
    private VendaRepositorio repositorio;
    @Autowired
    private VendaSelecionador selecionador;
    @Autowired
    private AdicionadorLinkVenda adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Venda> obterVenda(@PathVariable long id) {
        List<Venda> vendas = repositorio.findAll();
        Venda venda = selecionador.selecionar(vendas, id);
        if (venda == null) {
            ResponseEntity<Venda> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
        } else {
            adicionadorLink.adicionarLink(vendas);
			ResponseEntity<Venda> resposta = new ResponseEntity<Venda>(venda, HttpStatus.FOUND);
			return resposta;
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Venda>> obterVendas() {
        List<Venda> vendas = repositorio.findAll();
        if (vendas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(vendas);
			ResponseEntity<List<Venda>> resposta = new ResponseEntity<>(vendas, HttpStatus.FOUND);
			return resposta;
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarVenda(@RequestBody Venda venda) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (venda.getId() == null) {
            repositorio.save(venda);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarVenda(@RequestBody Venda atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Optional<Venda> optionalVenda = repositorio.findById(atualizacao.getId());
        Venda venda = new Venda();
        if (optionalVenda.isPresent()) {
            venda = optionalVenda.get();
        } else {
            venda = null;
        }
        if (venda != null) {
            VendaAtualizador atualizador = new VendaAtualizador();
            atualizador.atualizar(venda, atualizacao);
            repositorio.save(venda);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirVenda(@RequestBody Venda exclusao) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Optional<Venda> optionalVenda = repositorio.findById(exclusao.getId());
        Venda venda = new Venda();
        if (optionalVenda.isPresent()) {
            venda = optionalVenda.get();
        } else {
            venda = null;
        }
        if (venda != null) {
            repositorio.delete(venda);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}

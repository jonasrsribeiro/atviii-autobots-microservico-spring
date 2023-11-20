package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Veiculo;

import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.servicos.atualizador.VeiculoAtualizador;
import com.autobots.automanager.servicos.hateoasdor.AdicionadorLinkVeiculo;
import com.autobots.automanager.servicos.selecionador.VeiculoSelecionador;

@RestController
@RequestMapping("/veiculos")
public class VeiculoControle {
    @Autowired
    private VeiculoRepositorio repositorio;
    @Autowired
    private VeiculoSelecionador selecionador;
    @Autowired
    private AdicionadorLinkVeiculo adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> obterVeiculo(@PathVariable long id) {
        List<Veiculo> veiculos = repositorio.findAll();
        Veiculo veiculo = selecionador.selecionar(veiculos, id);
        if (veiculo == null) {
            ResponseEntity<Veiculo> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            adicionadorLink.adicionarLink(veiculos);
            ResponseEntity<Veiculo> resposta = new ResponseEntity<Veiculo>(veiculo, HttpStatus.FOUND);
            return resposta;
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Veiculo>> obterVeiculos() {
        List<Veiculo> veiculos = repositorio.findAll();
        if (veiculos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(veiculos);
            ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<>(veiculos, HttpStatus.FOUND);
            return resposta;
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (veiculo.getId() == null) {
            repositorio.save(veiculo);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarVeiculo(@RequestBody Veiculo atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Optional<Veiculo> optionalVeiculo = repositorio.findById(atualizacao.getId());
        Veiculo veiculo = new Veiculo();
        if (optionalVeiculo.isPresent()) {
            veiculo = optionalVeiculo.get();
        } else {
            veiculo = null;
        }
        if (veiculo != null) {
            VeiculoAtualizador atualizador = new VeiculoAtualizador();
            atualizador.atualizar(veiculo, atualizacao);
            repositorio.save(veiculo);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirVeiculo(@RequestBody Veiculo exclusao) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Optional<Veiculo> optionalVeiculo = repositorio.findById(exclusao.getId());
        Veiculo veiculo = new Veiculo();
        if (optionalVeiculo.isPresent()) {
            veiculo = optionalVeiculo.get();
        } else {
            veiculo = null;
        }
        if (veiculo != null) {
            repositorio.delete(veiculo);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}

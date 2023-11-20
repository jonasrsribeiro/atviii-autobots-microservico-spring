package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Empresa;

import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.servicos.atualizador.EmpresaAtualizador;
import com.autobots.automanager.servicos.hateoasdor.AdicionadorLinkEmpresa;
import com.autobots.automanager.servicos.selecionador.EmpresaSelecionador;

@RestController
@RequestMapping("/empresas")
public class EmpresaControle {
    @Autowired
    private EmpresaRepositorio repositorio;
    @Autowired
    private EmpresaSelecionador selecionador;
    @Autowired
    private AdicionadorLinkEmpresa adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obterEmpresa(@PathVariable long id) {
        List<Empresa> empresas = repositorio.findAll();
        Empresa empresa = selecionador.selecionar(empresas, id);
        if (empresa == null) {
            ResponseEntity<Empresa> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
        } else {
            adicionadorLink.adicionarLink(empresas);
			ResponseEntity<Empresa> resposta = new ResponseEntity<Empresa>(empresa, HttpStatus.FOUND);
			return resposta;
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Empresa>> obterEmpresas() {
        List<Empresa> empresas = repositorio.findAll();
        if (empresas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(empresas);
			ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(empresas, HttpStatus.FOUND);
			return resposta;
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody Empresa empresa) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (empresa.getId() == null) {
            repositorio.save(empresa);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarEmpresa(@RequestBody Empresa atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Optional<Empresa> optionalEmpresa = repositorio.findById(atualizacao.getId());
        Empresa empresa = new Empresa();
        if (optionalEmpresa.isPresent()) {
            empresa = optionalEmpresa.get();
        } else {
            empresa = null;
        }
        if (empresa != null) {
            EmpresaAtualizador atualizador = new EmpresaAtualizador();
            atualizador.atualizar(empresa, atualizacao);
            repositorio.save(empresa);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirEmpresa(@RequestBody Empresa exclusao) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Optional<Empresa> optionalEmpresa = repositorio.findById(exclusao.getId());
        Empresa empresa = new Empresa();
        if (optionalEmpresa.isPresent()) {
            empresa = optionalEmpresa.get();
        } else {
            empresa = null;
        }
        if (empresa != null) {
            repositorio.delete(empresa);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}

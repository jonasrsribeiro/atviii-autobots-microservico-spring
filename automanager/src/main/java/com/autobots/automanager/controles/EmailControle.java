package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Email;

import com.autobots.automanager.repositorios.EmailRepositorio;
import com.autobots.automanager.servicos.atualizador.EmailAtualizador;
import com.autobots.automanager.servicos.hateoasdor.AdicionadorLinkEmail;
import com.autobots.automanager.servicos.selecionador.EmailSelecionador;

@RestController
@RequestMapping("/emails")
public class EmailControle {
    @Autowired
    private EmailRepositorio repositorio;
    @Autowired
    private EmailSelecionador selecionador;
    @Autowired
    private AdicionadorLinkEmail adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Email> obterEmail(@PathVariable long id) {
        List<Email> emails = repositorio.findAll();
        Email email = selecionador.selecionar(emails, id);
        if (email == null) {
            ResponseEntity<Email> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
        } else {
            adicionadorLink.adicionarLink(emails);
			ResponseEntity<Email> resposta = new ResponseEntity<Email>(email, HttpStatus.FOUND);
			return resposta;
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Email>> obterEmails() {
        List<Email> emails = repositorio.findAll();
        if (emails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(emails);
			ResponseEntity<List<Email>> resposta = new ResponseEntity<>(emails, HttpStatus.FOUND);
			return resposta;
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarEmail(@RequestBody Email email) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (email.getId() == null) {
            repositorio.save(email);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarEmail(@RequestBody Email atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Optional<Email> optionalEmail = repositorio.findById(atualizacao.getId());
        Email email = new Email();
        if (optionalEmail.isPresent()) {
            email = optionalEmail.get();
        } else {
            email = null;
        }
        if (email != null) {
            EmailAtualizador atualizador = new EmailAtualizador();
            atualizador.atualizar(email, atualizacao);
            repositorio.save(email);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirEmail(@RequestBody Email exclusao) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Optional<Email> optionalEmail = repositorio.findById(exclusao.getId());
        Email email = new Email();
        if (optionalEmail.isPresent()) {
            email = optionalEmail.get();
        } else {
            email = null;
        }
        if (email != null) {
            repositorio.delete(email);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}

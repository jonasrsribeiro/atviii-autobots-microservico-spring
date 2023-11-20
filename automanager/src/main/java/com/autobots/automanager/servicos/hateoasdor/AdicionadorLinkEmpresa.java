package com.autobots.automanager.servicos.hateoasdor;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.entidades.Empresa;

@Component
public class AdicionadorLinkEmpresa extends AdicionadorLinkFactory<Empresa> implements AdicionadorLink<Empresa> {

	@Override
    protected AdicionadorLink<Empresa> criarAdicionadorLink(Class<?> classe) {
        if (classe.equals(Empresa.class)) {
            return new AdicionadorLinkEmpresa();
        }

		return null;

	}

	@Override
    public void adicionarLink(Empresa objeto) {
        adicionarLinkEmpresa(objeto);
    }

	@Override
    public void adicionarLink(List<Empresa> lista) {
        for (Empresa empresa : lista) {
            adicionarLinkEmpresa(empresa);
        }
    }

	private void adicionarLinkEmpresa(Empresa empresa) {
        long id = empresa.getId();

        Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControle.class).obterEmpresa(id))
                .withSelfRel().withType("GET").withRel("self").withTitle("Obter detalhes do empresa");

        Link linkListaEmpresas = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControle.class).obterEmpresas())
                .withRel("todosEmpresas").withType("GET").withTitle("Obter lista de empresas");

        Link linkNovoEmpresa = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(EmpresaControle.class).cadastrarEmpresa(new Empresa()))
                .withRel("novoEmpresa").withType("POST").withTitle("Criar um novo empresa");

        Link linkAtualizar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(EmpresaControle.class).atualizarEmpresa(empresa))
                .withRel("atualizar").withType("PUT").withTitle("Atualizar detalhes do empresa");

        Link linkExcluir = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(EmpresaControle.class).excluirEmpresa(empresa)).withRel("excluir")
                .withType("DELETE").withTitle("Excluir empresa");

        empresa.add(linkProprio, linkListaEmpresas, linkNovoEmpresa, linkAtualizar, linkExcluir);
    }

	public void adicionarLinkListaEmpresas(List<Empresa> empresas) {
        Link linkListaEmpresas = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(EmpresaControle.class).obterEmpresas()).withRel("empresas")
                .withType("GET").withTitle("Obter lista de empresas");

        Link linkNovoEmpresa = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(EmpresaControle.class).cadastrarEmpresa(new Empresa()))
                .withRel("novoEmpresa").withType("POST").withTitle("Criar um novo empresa");

        empresas.forEach(empresa -> empresa.add(linkListaEmpresas, linkNovoEmpresa));
    }
}

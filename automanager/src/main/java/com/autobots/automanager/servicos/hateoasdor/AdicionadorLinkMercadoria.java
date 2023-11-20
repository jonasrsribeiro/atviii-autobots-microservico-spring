package com.autobots.automanager.servicos.hateoasdor;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.entidades.Mercadoria;

@Component
public class AdicionadorLinkMercadoria extends AdicionadorLinkFactory<Mercadoria> implements AdicionadorLink<Mercadoria> {

	@Override
    protected AdicionadorLink<Mercadoria> criarAdicionadorLink(Class<?> classe) {
        if (classe.equals(Mercadoria.class)) {
            return new AdicionadorLinkMercadoria();
        }

		return null;

	}

	@Override
    public void adicionarLink(Mercadoria objeto) {
        adicionarLinkMercadoria(objeto);
    }

	@Override
    public void adicionarLink(List<Mercadoria> lista) {
        for (Mercadoria mercadoria : lista) {
            adicionarLinkMercadoria(mercadoria);
        }
    }

	private void adicionarLinkMercadoria(Mercadoria mercadoria) {
        long id = mercadoria.getId();

        Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControle.class).obterMercadoria(id))
                .withSelfRel().withType("GET").withRel("self").withTitle("Obter detalhes do mercadoria");

        Link linkListaMercadorias = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControle.class).obterMercadorias())
                .withRel("todosMercadorias").withType("GET").withTitle("Obter lista de mercadorias");

        Link linkNovoMercadoria = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControle.class).cadastrarMercadoria(new Mercadoria()))
                .withRel("novoMercadoria").withType("POST").withTitle("Criar um novo mercadoria");

        Link linkAtualizar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControle.class).atualizarMercadoria(mercadoria))
                .withRel("atualizar").withType("PUT").withTitle("Atualizar detalhes do mercadoria");

        Link linkExcluir = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControle.class).excluirMercadoria(mercadoria)).withRel("excluir")
                .withType("DELETE").withTitle("Excluir mercadoria");

        mercadoria.add(linkProprio, linkListaMercadorias, linkNovoMercadoria, linkAtualizar, linkExcluir);
    }

	public void adicionarLinkListaMercadorias(List<Mercadoria> mercadorias) {
        Link linkListaMercadorias = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControle.class).obterMercadorias()).withRel("mercadorias")
                .withType("GET").withTitle("Obter lista de mercadorias");

        Link linkNovoMercadoria = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControle.class).cadastrarMercadoria(new Mercadoria()))
                .withRel("novoMercadoria").withType("POST").withTitle("Criar um novo mercadoria");

        mercadorias.forEach(mercadoria -> mercadoria.add(linkListaMercadorias, linkNovoMercadoria));
    }
}

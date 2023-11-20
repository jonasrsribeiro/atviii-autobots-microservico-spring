package com.autobots.automanager.servicos.atualizador;

import java.util.Set;

import com.autobots.automanager.entidades.Mercadoria;

public class MercadoriaAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void atualizar(Mercadoria mercadoria, Mercadoria atualizacao) {
        if (atualizacao.getValidade() != null) {
            mercadoria.setValidade(atualizacao.getValidade());
        }
        if (atualizacao.getFabricao() != null) {
            mercadoria.setFabricao(atualizacao.getFabricao());
        }
        if (atualizacao.getCadastro() != null) {
            mercadoria.setCadastro(atualizacao.getCadastro());
        }
        if (!verificador.verificar(atualizacao.getNome())) {
            mercadoria.setNome(atualizacao.getNome());
        }
        if (atualizacao.getQuantidade() != 0) {
            mercadoria.setQuantidade(atualizacao.getQuantidade());
        }
        if (atualizacao.getValor() != 0.0) {
            mercadoria.setValor(atualizacao.getValor());
        }
        if (!verificador.verificar(atualizacao.getDescricao())) {
            mercadoria.setDescricao(atualizacao.getDescricao());
        }
    }

    public void atualizar(Set<Mercadoria> mercadorias, Set<Mercadoria> atualizacoes) {
        for (Mercadoria atualizacao : atualizacoes) {
            for (Mercadoria mercadoria : mercadorias) {
                if (atualizacao.getId() == mercadoria.getId()) {
                    atualizar(mercadoria, atualizacao);
                    break;
                }
            }
        }
    }
}

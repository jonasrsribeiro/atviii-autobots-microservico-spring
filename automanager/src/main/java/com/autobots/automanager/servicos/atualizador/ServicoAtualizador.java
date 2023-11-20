package com.autobots.automanager.servicos.atualizador;

import java.util.Set;

import com.autobots.automanager.entidades.Servico;

public class ServicoAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void atualizar(Servico servico, Servico atualizacao) {
        if (!verificador.verificar(atualizacao.getNome())) {
            servico.setNome(atualizacao.getNome());
        }
        if (atualizacao.getValor() != 0.0) {
            servico.setValor(atualizacao.getValor());
        }
        if (!verificador.verificar(atualizacao.getDescricao())) {
            servico.setDescricao(atualizacao.getDescricao());
        }
    }

    public void atualizar(Set<Servico> servicos, Set<Servico> atualizacoes) {
        for (Servico atualizacao : atualizacoes) {
            for (Servico servico : servicos) {
                if (atualizacao.getId().equals(servico.getId())) {
                    atualizar(servico, atualizacao);
                    break;
                }
            }
        }
    }
}

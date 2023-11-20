package com.autobots.automanager.servicos.atualizador;

import java.util.Set;

import com.autobots.automanager.entidades.Venda;

public class VendaAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();
    private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();
    private DocumentoAtualizador documentoAtualizador = new DocumentoAtualizador();
    private EmailAtualizador emailAtualizador = new EmailAtualizador();
    private MercadoriaAtualizador mercadoriaAtualizador = new MercadoriaAtualizador();
    private ServicoAtualizador servicoAtualizador = new ServicoAtualizador();
    private VeiculoAtualizador veiculoAtualizador = new VeiculoAtualizador();

    private class VendaAtualizadorDados {
        void atualizar(Venda venda, Venda atualizacao) {
            if (!verificador.verificar(atualizacao.getIdentificacao())) {
                venda.setIdentificacao(atualizacao.getIdentificacao());
            }
        }
    }

    public void atualizar(Venda venda, Venda atualizacao) {
        VendaAtualizadorDados dados = new VendaAtualizadorDados();
        dados.atualizar(venda, atualizacao);
        telefoneAtualizador.atualizar(venda.getTelefones(), atualizacao.getTelefones());
        documentoAtualizador.atualizar(venda.getDocumentos(), atualizacao.getDocumentos());
        emailAtualizador.atualizar(venda.getEmails(), atualizacao.getEmails());
        mercadoriaAtualizador.atualizar(venda.getMercadorias(), atualizacao.getMercadorias());
        servicoAtualizador.atualizar(venda.getServicos(), atualizacao.getServicos());
        veiculoAtualizador.atualizar(venda.getVeiculo(), atualizacao.getVeiculo());
    }

    public void atualizar(Set<Venda> vendas, Set<Venda> atualizacoes) {
        for (Venda atualizacao : atualizacoes) {
            for (Venda venda : vendas) {
                if (atualizacao.getId().equals(venda.getId())) {
                    atualizar(venda, atualizacao);
                    break;
                }
            }
        }
    }
}

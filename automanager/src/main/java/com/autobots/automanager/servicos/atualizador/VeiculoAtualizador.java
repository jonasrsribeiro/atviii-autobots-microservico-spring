package com.autobots.automanager.servicos.atualizador;

import com.autobots.automanager.entidades.Veiculo;

public class VeiculoAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    private class VeiculoAtualizadorDados {
        void atualizar(Veiculo veiculo, Veiculo atualizacao) {
            if (!verificador.verificar(atualizacao.getModelo())) {
                veiculo.setModelo(atualizacao.getModelo());
            }
            if (!verificador.verificar(atualizacao.getPlaca())) {
                veiculo.setPlaca(atualizacao.getPlaca());
            }
        }
    }

    public void atualizar(Veiculo veiculo, Veiculo atualizacao) {
        VeiculoAtualizadorDados dados = new VeiculoAtualizadorDados();
        dados.atualizar(veiculo, atualizacao);

        if (atualizacao.getProprietario() != null) {
            veiculo.setProprietario(atualizacao.getProprietario());
        }
    }
}

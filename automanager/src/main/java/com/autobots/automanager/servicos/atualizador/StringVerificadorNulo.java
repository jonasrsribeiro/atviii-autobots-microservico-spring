package com.autobots.automanager.servicos.atualizador;

public class StringVerificadorNulo {

	public boolean verificar(String dado) {
		boolean nulo = true;
		if (!(dado == null)) {
			if (!dado.isBlank()) {
				nulo = false;
			}
		}
		return nulo;
	}

    public boolean preenchido(String numeroAtualizacao) {
        return false;
    }
}
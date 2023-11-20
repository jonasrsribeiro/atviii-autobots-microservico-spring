package com.autobots.automanager.servicos.atualizador;

import java.util.Objects;
import java.util.Set;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.enumeracoes.TipoDocumento;

public class DocumentoAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void atualizar(Documento documento, Documento atualizacao) {
        if (Objects.nonNull(atualizacao)) {
            atualizarTipo(documento, atualizacao.getTipo());
            atualizarNumero(documento, atualizacao.getNumero());
        }
    }

    public void atualizar(Set<Documento> documentos, Set<Documento> atualizacoes) {
		for (Documento atualizacao : atualizacoes) {
			for (Documento documento : documentos) {
				if (Objects.equals(atualizacao.getId(), documento.getId())) {
					atualizar(documento, atualizacao);
					break;
				}
			}
		}
	}	

    private void atualizarTipo(Documento documento, TipoDocumento tipoAtualizacao) {
        if (Objects.nonNull(tipoAtualizacao)) {
            documento.setTipo(tipoAtualizacao);
        }
    }

    private void atualizarNumero(Documento documento, String numeroAtualizacao) {
        if (verificador.preenchido(numeroAtualizacao)) {
            documento.setNumero(numeroAtualizacao);
        }
    }
}


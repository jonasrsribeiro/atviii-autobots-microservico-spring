package com.autobots.automanager.servicos.selecionador;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Documento;

@Component
public class DocumentoSelecionador extends SelecionadorPorIdTemplateMethod<Documento> {
	@Override
	public Documento selecionar(List<Documento> documentos, Long id) {
		return buscar(documentos, id);
	}
	
}
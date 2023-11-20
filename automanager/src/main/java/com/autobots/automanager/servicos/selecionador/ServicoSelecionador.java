package com.autobots.automanager.servicos.selecionador;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Servico;

@Component
public class ServicoSelecionador extends SelecionadorPorIdTemplateMethod<Servico> {
	@Override
	public Servico selecionar(List<Servico> servicos, Long id) {
		return buscar(servicos, id);
	}
	
}
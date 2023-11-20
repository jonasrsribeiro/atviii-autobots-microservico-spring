package com.autobots.automanager.servicos.selecionador;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Telefone;

@Component
public class TelefoneSelecionador extends SelecionadorPorIdTemplateMethod<Telefone> {
	@Override
	public Telefone selecionar(List<Telefone> telefones, Long id) {
		return buscar(telefones, id);
	}
	
}
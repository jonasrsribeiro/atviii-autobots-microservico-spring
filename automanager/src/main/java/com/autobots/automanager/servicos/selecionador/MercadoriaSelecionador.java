package com.autobots.automanager.servicos.selecionador;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Mercadoria;

@Component
public class MercadoriaSelecionador extends SelecionadorPorIdTemplateMethod<Mercadoria> {
	@Override
	public Mercadoria selecionar(List<Mercadoria> mercadorias, Long id) {
		return buscar(mercadorias, id);
	}
	
}
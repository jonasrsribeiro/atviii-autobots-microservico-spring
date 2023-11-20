package com.autobots.automanager.servicos.selecionador;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Venda;

@Component
public class VendaSelecionador extends SelecionadorPorIdTemplateMethod<Venda> {
	@Override
	public Venda selecionar(List<Venda> vendas, Long id) {
		return buscar(vendas, id);
	}
	
}
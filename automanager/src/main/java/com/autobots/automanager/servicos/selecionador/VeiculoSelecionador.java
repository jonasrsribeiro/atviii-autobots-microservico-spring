package com.autobots.automanager.servicos.selecionador;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Veiculo;

@Component
public class VeiculoSelecionador extends SelecionadorPorIdTemplateMethod<Veiculo> {
	@Override
	public Veiculo selecionar(List<Veiculo> veiculos, Long id) {
		return buscar(veiculos, id);
	}
	
}
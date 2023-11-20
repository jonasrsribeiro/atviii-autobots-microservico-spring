package com.autobots.automanager.servicos.selecionador;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Empresa;

@Component
public class EmpresaSelecionador extends SelecionadorPorIdTemplateMethod<Empresa> {
	@Override
	public Empresa selecionar(List<Empresa> empresas, Long id) {
		return buscar(empresas, id);
	}
	
}
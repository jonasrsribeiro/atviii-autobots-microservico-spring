package com.autobots.automanager.servicos.selecionador;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Usuario;

@Component
public class UsuarioSelecionador extends SelecionadorPorIdTemplateMethod<Usuario> {
	@Override
	public Usuario selecionar(List<Usuario> usuarios, Long id) {
		return buscar(usuarios, id);
	}
	
}
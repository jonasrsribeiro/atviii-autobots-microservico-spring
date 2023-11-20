package com.autobots.automanager.servicos.selecionador;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Email;

@Component
public class EmailSelecionador extends SelecionadorPorIdTemplateMethod<Email> {
	@Override
	public Email selecionar(List<Email> emails, Long id) {
		return buscar(emails, id);
	}
	
}
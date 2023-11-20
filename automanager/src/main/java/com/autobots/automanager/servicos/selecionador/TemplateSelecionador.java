package com.autobots.automanager.servicos.selecionador;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.entidades.Usuario;

public abstract class TemplateSelecionador extends RepresentationModel<Usuario> {
    public abstract Long getId();
}

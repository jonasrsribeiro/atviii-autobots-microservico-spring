package com.autobots.automanager.servicos.selecionador;

import java.util.List;


public abstract class SelecionadorPorIdTemplateMethod<T extends SelecionadorComId> {
    public abstract T selecionar(List<T> entidades, Long id);

    protected T buscar(List<T> entidades, Long id) {
        for (T entidade : entidades) {
            if (entidade.getId().equals(id)) {
                return entidade;
            }
        }
        return null;
    }
}

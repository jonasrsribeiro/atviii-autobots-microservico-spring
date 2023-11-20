package com.autobots.automanager.servicos.hateoasdor;

public abstract class AdicionadorLinkFactory<T> {
    protected abstract AdicionadorLink<T> criarAdicionadorLink(Class<?> classe);
}

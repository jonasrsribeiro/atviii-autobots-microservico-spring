package com.autobots.automanager.servicos.hateoasdor;

import java.util.List;

public interface AdicionadorLink<T> {
    public void adicionarLink(T objeto);
	void adicionarLink(List<T> lista);
}
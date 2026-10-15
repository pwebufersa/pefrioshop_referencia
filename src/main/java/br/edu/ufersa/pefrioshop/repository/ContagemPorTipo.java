package br.edu.ufersa.pefrioshop.repository;

import br.edu.ufersa.pefrioshop.domain.TipoProduto;

/**
 * Projecao usada na contagem de produtos por tipo (US 15).
 */
public interface ContagemPorTipo {

	TipoProduto getTipo();

	long getQuantidade();

}

package br.edu.ufersa.pefrioshop.service;

import br.edu.ufersa.pefrioshop.domain.Produto;
import java.math.BigDecimal;

/**
 * Produto com o frete estimado para Marte e o total do par (US 13).
 *
 * <p>Uso: {@code new ProdutoComFrete(produto, frete).total()}.
 */
public record ProdutoComFrete(Produto produto, BigDecimal frete) {

	/** Valor de venda do par, sem o frete. */
	public BigDecimal preco() {
		return produto.getPrecoVenda();
	}

	/** Valor do par somado ao frete estimado. */
	public BigDecimal total() {
		return preco().add(frete);
	}

}

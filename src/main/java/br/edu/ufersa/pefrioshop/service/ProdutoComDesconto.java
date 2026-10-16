package br.edu.ufersa.pefrioshop.service;

import br.edu.ufersa.pefrioshop.domain.Produto;
import java.math.BigDecimal;

/** Produto com o preco ja descontado (US 14). */
public record ProdutoComDesconto(Produto produto, BigDecimal precoComDesconto) {

	/** Valor de venda do produto, antes do desconto. */
	public BigDecimal precoOriginal() {
		return produto.getPrecoVenda();
	}

}

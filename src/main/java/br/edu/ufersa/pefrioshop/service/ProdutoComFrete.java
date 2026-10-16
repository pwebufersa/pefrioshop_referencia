package br.edu.ufersa.pefrioshop.service;

import br.edu.ufersa.pefrioshop.domain.Produto;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Produto com o frete estimado para Marte e o total do par (US 13).
 *
 * <p>O frete parte do valor fixo da viagem e cresce com o peso transportado,
 * pelo preco do quilo publicado pela SpaceX para carga ao solo marciano.
 *
 * <p>Uso: {@code new ProdutoComFrete(produto, baseline, porQuilo).total()}.
 */
public record ProdutoComFrete(Produto produto, BigDecimal baseline, BigDecimal porQuilo) {

	/** Valor de venda do par, sem o frete. */
	public BigDecimal preco() {
		return produto.getPrecoVenda();
	}

	/** Peso do par, em quilos. */
	public BigDecimal peso() {
		return produto.getPesoKg();
	}

	/** Frete da viagem somado ao peso transportado. */
	public BigDecimal frete() {
		return baseline.add(peso().multiply(porQuilo)).setScale(2, RoundingMode.HALF_UP);
	}

	/** Valor do par somado ao frete estimado. */
	public BigDecimal total() {
		return preco().add(frete());
	}

}

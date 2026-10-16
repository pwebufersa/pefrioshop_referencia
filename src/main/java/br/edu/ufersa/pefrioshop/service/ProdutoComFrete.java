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

	private static final BigDecimal GRAMAS_POR_QUILO = new BigDecimal("1000");

	/** Valor de venda do par, sem o frete. */
	public BigDecimal preco() {
		return produto.getPrecoVenda();
	}

	/** Peso do par, em gramas, como o produto guarda. */
	public int pesoGramas() {
		return produto.getPesoGramas();
	}

	/**
	 * Parte do peso no frete, em reais.
	 *
	 * <p>O peso entra em gramas e o preco e por quilo, o que exige a divisao por
	 * mil. A conta e feita em BigDecimal, com duas casas e arredondamento, porque
	 * a divisao inteira de gramas por mil daria zero para qualquer par abaixo de
	 * um quilo: o numero pequeno desapareceria dentro do numero grande, e o frete
	 * sairia sem o peso.
	 */
	public BigDecimal parcelaDoPeso() {
		return new BigDecimal(pesoGramas()).multiply(porQuilo).divide(GRAMAS_POR_QUILO, 2, RoundingMode.HALF_UP);
	}

	/** Frete da viagem somado ao peso transportado. */
	public BigDecimal frete() {
		return baseline.add(parcelaDoPeso());
	}

	/** Valor do par somado ao frete estimado. */
	public BigDecimal total() {
		return preco().add(frete());
	}

}

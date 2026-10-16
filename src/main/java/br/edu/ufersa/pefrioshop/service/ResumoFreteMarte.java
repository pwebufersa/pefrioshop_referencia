package br.edu.ufersa.pefrioshop.service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Itens do frete para Marte e os totais da tabela (US 13).
 *
 * <p>Os totais são apurados no serviço, e não no template, para manter o cálculo
 * fora da camada de apresentação.
 *
 * <p>Uso: {@code ResumoFreteMarte.de(itens)}.
 */
public record ResumoFreteMarte(List<ProdutoComFrete> itens, int totalPesoGramas, BigDecimal totalPreco,
		BigDecimal totalFrete, BigDecimal totalGeral) {

	/** Soma os pesos, os preços e os fretes dos itens e monta o resumo da tabela. */
	public static ResumoFreteMarte de(List<ProdutoComFrete> itens) {
		int peso = itens.stream().mapToInt(ProdutoComFrete::pesoGramas).sum();
		BigDecimal preco = itens.stream().map(ProdutoComFrete::preco).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal frete = itens.stream().map(ProdutoComFrete::frete).reduce(BigDecimal.ZERO, BigDecimal::add);
		return new ResumoFreteMarte(itens, peso, preco, frete, preco.add(frete));
	}

}

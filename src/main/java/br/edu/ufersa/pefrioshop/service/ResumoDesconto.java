package br.edu.ufersa.pefrioshop.service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Itens do desconto dos quatro produtos mais novos e os totais da tabela (US 14).
 *
 * <p>Os totais são apurados no serviço, e não no template, para manter o cálculo
 * fora da camada de apresentação.
 *
 * <p>Uso: {@code ResumoDesconto.de(itens)}.
 */
public record ResumoDesconto(List<ProdutoComDesconto> itens, BigDecimal totalOriginal, BigDecimal totalComDesconto) {

	/** Soma os preços originais e os descontados e monta o resumo da tabela. */
	public static ResumoDesconto de(List<ProdutoComDesconto> itens) {
		BigDecimal original = itens.stream().map(ProdutoComDesconto::precoOriginal).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal descontado = itens.stream().map(ProdutoComDesconto::precoComDesconto).reduce(BigDecimal.ZERO, BigDecimal::add);
		return new ResumoDesconto(itens, original, descontado);
	}

}

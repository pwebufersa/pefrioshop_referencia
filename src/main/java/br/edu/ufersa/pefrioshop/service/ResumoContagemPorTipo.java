package br.edu.ufersa.pefrioshop.service;

import br.edu.ufersa.pefrioshop.repository.ContagemPorTipo;
import java.util.List;

/**
 * Contagem de produtos por tipo e o total da tabela (US 15).
 *
 * <p>O total é apurado no serviço, e não no template, para manter o cálculo fora
 * da camada de apresentação.
 *
 * <p>Uso: {@code ResumoContagemPorTipo.de(itens)}.
 */
public record ResumoContagemPorTipo(List<ContagemPorTipo> itens, long totalQuantidade) {

	/** Soma as quantidades e monta o resumo da tabela. */
	public static ResumoContagemPorTipo de(List<ContagemPorTipo> itens) {
		return new ResumoContagemPorTipo(itens, itens.stream().mapToLong(ContagemPorTipo::getQuantidade).sum());
	}

}

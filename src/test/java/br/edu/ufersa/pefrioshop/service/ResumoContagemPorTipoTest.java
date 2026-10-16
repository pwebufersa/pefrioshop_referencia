package br.edu.ufersa.pefrioshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.edu.ufersa.pefrioshop.domain.TipoProduto;
import br.edu.ufersa.pefrioshop.repository.ContagemPorTipo;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Conferência do total da contagem de produtos por tipo (US 15). */
class ResumoContagemPorTipoTest {

	@Test
	void resumoApuraAQuantidade() {
		List<ContagemPorTipo> itens = List.of(
				new Contagem(TipoProduto.TENIS, 3),
				new Contagem(TipoProduto.CHINELO, 2),
				new Contagem(TipoProduto.BOTA, 1));

		ResumoContagemPorTipo resumo = ResumoContagemPorTipo.de(itens);

		assertEquals(6, resumo.totalQuantidade());
		assertEquals(3, resumo.itens().size());
	}

	@Test
	void resumoSemItensZera() {
		assertEquals(0, ResumoContagemPorTipo.de(List.of()).totalQuantidade());
	}

	/** Linha de contagem usada na conferência, no lugar da projeção do repositório. */
	private record Contagem(TipoProduto tipo, long quantidade) implements ContagemPorTipo {

		@Override
		public TipoProduto getTipo() {
			return tipo;
		}

		@Override
		public long getQuantidade() {
			return quantidade;
		}

	}

}

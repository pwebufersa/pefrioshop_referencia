package br.edu.ufersa.pefrioshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.edu.ufersa.pefrioshop.domain.Produto;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Conferência dos totais do desconto dos quatro produtos mais novos (US 14). */
class ResumoDescontoTest {

	@Test
	void resumoApuraOsPrecos() {
		List<ProdutoComDesconto> itens = List.of(
				new ProdutoComDesconto(produto("349.90"), new BigDecimal("324.36")),
				new ProdutoComDesconto(produto("149.90"), new BigDecimal("138.96")));

		ResumoDesconto resumo = ResumoDesconto.de(itens);

		assertEquals(new BigDecimal("499.80"), resumo.totalOriginal());
		assertEquals(new BigDecimal("463.32"), resumo.totalComDesconto());
	}

	@Test
	void resumoSemItensZera() {
		ResumoDesconto resumo = ResumoDesconto.de(List.of());

		assertEquals(BigDecimal.ZERO, resumo.totalOriginal());
		assertEquals(BigDecimal.ZERO, resumo.totalComDesconto());
	}

	private Produto produto(String precoVenda) {
		Produto produto = new Produto();
		produto.setPrecoVenda(new BigDecimal(precoVenda));
		return produto;
	}

}

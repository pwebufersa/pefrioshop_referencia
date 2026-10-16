package br.edu.ufersa.pefrioshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.edu.ufersa.pefrioshop.domain.Produto;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Conferência do cálculo do frete para Marte (US 13). */
class ResumoFreteMarteTest {

	private static final BigDecimal FRETE = new BigDecimal("987654.33");

	@Test
	void totalDaSomaPrecoComFrete() {
		ProdutoComFrete linha = new ProdutoComFrete(produto("149.90"), FRETE);

		assertEquals(new BigDecimal("987804.23"), linha.total());
	}

	@Test
	void resumoApuraPrecoFreteETotal() {
		List<ProdutoComFrete> itens = List.of(
				new ProdutoComFrete(produto("149.90"), FRETE),
				new ProdutoComFrete(produto("29.90"), FRETE));

		ResumoFreteMarte resumo = ResumoFreteMarte.de(itens);

		assertEquals(new BigDecimal("179.80"), resumo.totalPreco());
		assertEquals(new BigDecimal("1975308.66"), resumo.totalFrete());
		assertEquals(new BigDecimal("179.80").add(new BigDecimal("1975308.66")), resumo.totalGeral());
	}

	@Test
	void resumoSemItensZera() {
		ResumoFreteMarte resumo = ResumoFreteMarte.de(List.of());

		assertEquals(BigDecimal.ZERO, resumo.totalPreco());
		assertEquals(BigDecimal.ZERO, resumo.totalGeral());
	}

	private Produto produto(String precoVenda) {
		Produto produto = new Produto();
		produto.setPrecoVenda(new BigDecimal(precoVenda));
		return produto;
	}

}

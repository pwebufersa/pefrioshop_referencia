package br.edu.ufersa.pefrioshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.edu.ufersa.pefrioshop.domain.Produto;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Conferência do cálculo do frete para Marte, pela viagem e pelo peso (US 13). */
class ResumoFreteMarteTest {

	private static final BigDecimal VIAGEM = new BigDecimal("987654.33");
	private static final BigDecimal QUILO = new BigDecimal("508490.00");

	@Test
	void freteCresceComOPeso() {
		ProdutoComFrete leve = new ProdutoComFrete(produto("149.90", "0.350"), VIAGEM, QUILO);
		ProdutoComFrete pesado = new ProdutoComFrete(produto("149.90", "1.250"), VIAGEM, QUILO);

		assertEquals(new BigDecimal("1165625.83"), leve.frete());
		assertEquals(new BigDecimal("1623266.83"), pesado.frete());
		assertTrue(pesado.frete().compareTo(leve.frete()) > 0);
	}

	@Test
	void totalDaSomaPrecoComFrete() {
		ProdutoComFrete linha = new ProdutoComFrete(produto("149.90", "0.350"), VIAGEM, QUILO);

		assertEquals(new BigDecimal("1165775.73"), linha.total());
	}

	@Test
	void resumoApuraPrecoFreteETotal() {
		List<ProdutoComFrete> itens = List.of(
				new ProdutoComFrete(produto("149.90", "0.350"), VIAGEM, QUILO),
				new ProdutoComFrete(produto("29.90", "0.150"), VIAGEM, QUILO));

		ResumoFreteMarte resumo = ResumoFreteMarte.de(itens);

		assertEquals(new BigDecimal("179.80"), resumo.totalPreco());
		assertEquals(new BigDecimal("1165625.83").add(new BigDecimal("1063927.83")), resumo.totalFrete());
		assertEquals(new BigDecimal("179.80").add(resumo.totalFrete()), resumo.totalGeral());
	}

	@Test
	void resumoSemItensZera() {
		ResumoFreteMarte resumo = ResumoFreteMarte.de(List.of());

		assertEquals(BigDecimal.ZERO, resumo.totalPreco());
		assertEquals(BigDecimal.ZERO, resumo.totalGeral());
	}

	private Produto produto(String precoVenda, String pesoKg) {
		Produto produto = new Produto();
		produto.setPrecoVenda(new BigDecimal(precoVenda));
		produto.setPesoKg(new BigDecimal(pesoKg));
		return produto;
	}

}

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
		ProdutoComFrete leve = new ProdutoComFrete(produto("149.90", 350), VIAGEM, QUILO);
		ProdutoComFrete pesado = new ProdutoComFrete(produto("149.90", 1250), VIAGEM, QUILO);

		assertEquals(new BigDecimal("1165625.83"), leve.frete());
		assertEquals(new BigDecimal("1623266.83"), pesado.frete());
		assertTrue(pesado.frete().compareTo(leve.frete()) > 0);
	}

	@Test
	void pesoEmGramasNaoDesapareceNaContaGrande() {
		// Em aritmética inteira, 620 gramas divididas por mil dão zero, e o peso de
		// um par sumiria do frete, que é um valor de centenas de milhares. A
		// conversão precisa de casas decimais.
		assertEquals(0, 620 / 1000);

		ProdutoComFrete linha = new ProdutoComFrete(produto("149.90", 620), VIAGEM, QUILO);

		assertEquals(new BigDecimal("315263.80"), linha.parcelaDoPeso());
		assertEquals(new BigDecimal("1302918.13"), linha.frete());
	}

	@Test
	void totalDaSomaPrecoComFrete() {
		ProdutoComFrete linha = new ProdutoComFrete(produto("149.90", 350), VIAGEM, QUILO);

		assertEquals(new BigDecimal("1165775.73"), linha.total());
	}

	@Test
	void resumoApuraPesoPrecoFreteETotal() {
		List<ProdutoComFrete> itens = List.of(
				new ProdutoComFrete(produto("149.90", 350), VIAGEM, QUILO),
				new ProdutoComFrete(produto("29.90", 150), VIAGEM, QUILO));

		ResumoFreteMarte resumo = ResumoFreteMarte.de(itens);

		assertEquals(500, resumo.totalPesoGramas());
		assertEquals(new BigDecimal("179.80"), resumo.totalPreco());
		assertEquals(new BigDecimal("1165625.83").add(new BigDecimal("1063927.83")), resumo.totalFrete());
		assertEquals(new BigDecimal("179.80").add(resumo.totalFrete()), resumo.totalGeral());
	}

	@Test
	void resumoSemItensZera() {
		ResumoFreteMarte resumo = ResumoFreteMarte.de(List.of());

		assertEquals(0, resumo.totalPesoGramas());
		assertEquals(BigDecimal.ZERO, resumo.totalPreco());
		assertEquals(BigDecimal.ZERO, resumo.totalGeral());
	}

	private Produto produto(String precoVenda, int pesoGramas) {
		Produto produto = new Produto();
		produto.setPrecoVenda(new BigDecimal(precoVenda));
		produto.setPesoGramas(pesoGramas);
		return produto;
	}

}

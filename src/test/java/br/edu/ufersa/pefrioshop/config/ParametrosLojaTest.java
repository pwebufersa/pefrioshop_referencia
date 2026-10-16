package br.edu.ufersa.pefrioshop.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/** Conferência da conversão do frete por quilo (US 13). */
class ParametrosLojaTest {

	private static final ParametrosLoja PARAMETROS = new ParametrosLoja(
			new BigDecimal("0.185"),
			new BigDecimal("0.05"),
			new BigDecimal("0.073"),
			new BigDecimal("987654.33"),
			new BigDecimal("100000.00"),
			new BigDecimal("5.0849"),
			30,
			"5584999990000");

	@Test
	void quiloEmReaisSegueOCambio() {
		assertEquals(new BigDecimal("508490.00"), PARAMETROS.freteMartePorQuilo());
	}

	@Test
	void cambioMaiorEncareceOQuilo() {
		ParametrosLoja dolarAlto = new ParametrosLoja(
				PARAMETROS.imposto(),
				PARAMETROS.descontoEmail(),
				PARAMETROS.descontoRelatorio(),
				PARAMETROS.freteMarteBaseline(),
				PARAMETROS.freteMartePorQuiloDolar(),
				new BigDecimal("6.0000"),
				PARAMETROS.diasEmailPromocional(),
				PARAMETROS.whatsapp());

		assertEquals(new BigDecimal("600000.00"), dolarAlto.freteMartePorQuilo());
	}

}

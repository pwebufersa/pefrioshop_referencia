package br.edu.ufersa.pefrioshop.config;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Regras de negocio da loja, lidas de {@code application.properties}.
 *
 * <p>A intencao e nao deixar numeros soltos no codigo. A taxa de imposto, o
 * desconto do relatorio e o frete para Marte mudam de valor com o tempo, e o
 * valor deve existir em um unico lugar.
 *
 * <p>Uso: injetar no servico e chamar {@code parametros.imposto()}.
 */
@ConfigurationProperties(prefix = "pefrioshop")
public record ParametrosLoja(
		BigDecimal imposto,
		BigDecimal descontoEmail,
		BigDecimal descontoRelatorio,
		BigDecimal freteMarteBaseline,
		BigDecimal freteMartePorQuiloDolar,
		BigDecimal cambioDolar,
		int diasEmailPromocional,
		String whatsapp) {

	/**
	 * Frete por quilo transportado, em reais.
	 *
	 * <p>O preco por quilo e publicado pela SpaceX em dolar; a conversao para
	 * real e feita pelo cambio comercial informado na configuracao.
	 */
	public BigDecimal freteMartePorQuilo() {
		return freteMartePorQuiloDolar.multiply(cambioDolar).setScale(2, RoundingMode.HALF_UP);
	}

}

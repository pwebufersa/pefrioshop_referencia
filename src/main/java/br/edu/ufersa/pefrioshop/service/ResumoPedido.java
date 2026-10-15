package br.edu.ufersa.pefrioshop.service;

import java.math.BigDecimal;

/**
 * Resumo financeiro de um pedido. Os valores sao calculados, nunca guardados.
 */
public record ResumoPedido(BigDecimal valorTotal, BigDecimal imposto, BigDecimal totalComImposto) {
}

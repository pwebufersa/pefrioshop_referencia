package br.edu.ufersa.pefrioshop.service;

import java.math.BigDecimal;

/** Uma linha do relatorio de faturamento (US 5). */
public record FaturamentoMensal(String mes, BigDecimal faturamento) {
}

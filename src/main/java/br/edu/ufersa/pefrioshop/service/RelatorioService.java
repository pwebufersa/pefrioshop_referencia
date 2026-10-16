package br.edu.ufersa.pefrioshop.service;

import br.edu.ufersa.pefrioshop.config.ParametrosLoja;
import br.edu.ufersa.pefrioshop.domain.Pedido;
import br.edu.ufersa.pefrioshop.domain.Produto;
import br.edu.ufersa.pefrioshop.repository.PedidoRepository;
import br.edu.ufersa.pefrioshop.repository.ProdutoRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RelatorioService {

	private static final DateTimeFormatter MES_ANO = DateTimeFormatter.ofPattern("MM/yyyy");

	private final PedidoRepository pedidos;
	private final ProdutoRepository produtos;
	private final ParametrosLoja parametros;

	public RelatorioService(PedidoRepository pedidos, ProdutoRepository produtos, ParametrosLoja parametros) {
		this.pedidos = pedidos;
		this.produtos = produtos;
		this.parametros = parametros;
	}

	/** Faturamento dos 12 meses anteriores a data informada (US 5). */
	@Transactional(readOnly = true)
	public List<FaturamentoMensal> faturamento(LocalDate referencia) {
		YearMonth fim = YearMonth.from(referencia);
		List<FaturamentoMensal> linhas = new ArrayList<>();
		for (int i = 11; i >= 0; i--) {
			YearMonth mes = fim.minusMonths(i);
			BigDecimal total = pedidos
					.findByDataPedidoBetween(mes.atDay(1).atStartOfDay(), mes.atEndOfMonth().atTime(LocalTime.MAX))
					.stream()
					.map(Pedido::getValorTotal)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			linhas.add(new FaturamentoMensal(mes.format(MES_ANO), total));
		}
		return linhas;
	}

	public BigDecimal somarFaturamento(List<FaturamentoMensal> linhas) {
		return linhas.stream().map(FaturamentoMensal::faturamento).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	/** Imposto sobre um valor, com duas casas. */
	public BigDecimal impostoSobre(BigDecimal valor) {
		return valor.multiply(parametros.imposto()).setScale(2, RoundingMode.HALF_UP);
	}

	/** Produtos com frete estimado para Marte, com os totais, do menor ao maior número BR (US 13). */
	public ResumoFreteMarte freteParaMarte() {
		BigDecimal baseline = parametros.freteMarteBaseline();
		BigDecimal porQuilo = parametros.freteMartePorQuilo();
		List<ProdutoComFrete> itens = produtos.findAllByOrderByNumeroBrAsc().stream()
				.map(produto -> new ProdutoComFrete(produto, baseline, porQuilo))
				.toList();
		return ResumoFreteMarte.de(itens);
	}

	/** Os 4 produtos mais novos, com desconto, do maior para o menor preco (US 14). */
	public ResumoDesconto produtosComDesconto() {
		BigDecimal fator = BigDecimal.ONE.subtract(parametros.descontoRelatorio());
		List<ProdutoComDesconto> itens = produtos.findTop4ByOrderByDataCadastroDescIdDesc().stream()
				.map(produto -> new ProdutoComDesconto(produto,
						produto.getPrecoVenda().multiply(fator).setScale(2, RoundingMode.HALF_UP)))
				.sorted(Comparator.comparing(ProdutoComDesconto::precoComDesconto).reversed())
				.toList();
		return ResumoDesconto.de(itens);
	}

	/** Contagem de produtos por tipo (US 15). */
	public ResumoContagemPorTipo contagemPorTipo() {
		return ResumoContagemPorTipo.de(produtos.contarPorTipo());
	}

	public BigDecimal getTaxaImposto() {
		return parametros.imposto();
	}

	public BigDecimal getDescontoRelatorio() {
		return parametros.descontoRelatorio();
	}

	public BigDecimal getFreteMarteBaseline() {
		return parametros.freteMarteBaseline();
	}

	public BigDecimal getFreteMartePorQuilo() {
		return parametros.freteMartePorQuilo();
	}

	public BigDecimal getFreteMartePorQuiloDolar() {
		return parametros.freteMartePorQuiloDolar();
	}

	public BigDecimal getCambioDolar() {
		return parametros.cambioDolar();
	}

}

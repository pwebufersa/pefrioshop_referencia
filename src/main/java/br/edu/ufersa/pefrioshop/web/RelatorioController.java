package br.edu.ufersa.pefrioshop.web;

import br.edu.ufersa.pefrioshop.service.ClienteService;
import br.edu.ufersa.pefrioshop.service.FaturamentoMensal;
import br.edu.ufersa.pefrioshop.service.RelatorioService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

	private final RelatorioService relatorios;
	private final ClienteService clientes;

	public RelatorioController(RelatorioService relatorios, ClienteService clientes) {
		this.relatorios = relatorios;
		this.clientes = clientes;
	}

	/** Faturamento dos ultimos 12 meses (US 5). */
	@GetMapping("/faturamento")
	public String faturamento(
			@RequestParam(name = "referencia", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate referencia,
			Model model) {
		LocalDate data = referencia == null ? LocalDate.now() : referencia;
		List<FaturamentoMensal> linhas = relatorios.faturamento(data);
		BigDecimal total = relatorios.somarFaturamento(linhas);
		BigDecimal imposto = relatorios.impostoSobre(total);
		model.addAttribute("linhas", linhas);
		model.addAttribute("referencia", data);
		model.addAttribute("total", total);
		model.addAttribute("imposto", imposto);
		model.addAttribute("totalComImposto", total.add(imposto));
		model.addAttribute("taxaPercentual", relatorios.getTaxaImposto().movePointRight(2));
		return "relatorio/faturamento";
	}

	/** Aniversariantes do mes (US 11). */
	@GetMapping("/aniversariantes")
	public String aniversariantes(@RequestParam(name = "mes", required = false) Integer mes, Model model) {
		int mesConsultado = mes == null ? LocalDate.now().getMonthValue() : mes;
		model.addAttribute("clientes", clientes.aniversariantes(mesConsultado));
		model.addAttribute("mes", mesConsultado);
		return "relatorio/aniversariantes";
	}

	/** Frete estimado para Marte (US 13). */
	@GetMapping("/frete-marte")
	public String freteMarte(Model model) {
		model.addAttribute("itens", relatorios.freteParaMarte());
		model.addAttribute("fretePorPar", relatorios.getFreteMartePorPar());
		return "relatorio/frete-marte";
	}

	/** Desconto nos quatro produtos mais novos (US 14). */
	@GetMapping("/desconto")
	public String desconto(Model model) {
		model.addAttribute("itens", relatorios.produtosComDesconto());
		model.addAttribute("descontoPercentual", relatorios.getDescontoRelatorio().movePointRight(2));
		return "relatorio/desconto";
	}

	/** Contagem de produtos por tipo (US 15). */
	@GetMapping("/contagem-tipo")
	public String contagemTipo(Model model) {
		model.addAttribute("itens", relatorios.contagemPorTipo());
		return "relatorio/contagem-tipo";
	}

	/** Clientes cadastrados em um mes e ano (US 16). */
	@GetMapping("/novatos")
	public String novatos(@RequestParam(name = "mes", required = false) Integer mes,
			@RequestParam(name = "ano", required = false) Integer ano, Model model) {
		int mesConsultado = mes == null ? LocalDate.now().getMonthValue() : mes;
		int anoConsultado = ano == null ? LocalDate.now().getYear() : ano;
		model.addAttribute("clientes", clientes.novatos(mesConsultado, anoConsultado));
		model.addAttribute("mes", mesConsultado);
		model.addAttribute("ano", anoConsultado);
		return "relatorio/novatos";
	}

}

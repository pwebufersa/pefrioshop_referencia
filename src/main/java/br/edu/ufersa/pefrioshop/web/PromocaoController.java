package br.edu.ufersa.pefrioshop.web;

import br.edu.ufersa.pefrioshop.service.ClienteService;
import br.edu.ufersa.pefrioshop.service.PromocaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** E-mail promocional (US 9). */
@Controller
@RequestMapping("/promocoes")
public class PromocaoController {

	private final PromocaoService promocoes;
	private final ClienteService clientes;

	public PromocaoController(PromocaoService promocoes, ClienteService clientes) {
		this.promocoes = promocoes;
		this.clientes = clientes;
	}

	@GetMapping
	public String pagina(Model model) {
		model.addAttribute("clientes", clientes.listar());
		return "promocao";
	}

	@PostMapping
	public String gerar(@RequestParam(name = "clienteId") Long clienteId, Model model) {
		model.addAttribute("clientes", clientes.listar());
		model.addAttribute("clienteId", clienteId);
		promocoes.gerar(clienteId).ifPresentOrElse(
				email -> model.addAttribute("email", email),
				() -> model.addAttribute("aviso",
						"Nenhuma promoção disponível para este cliente: sem pedido, dentro do prazo de 30 dias, "
								+ "ou sem tênis no último pedido."));
		return "promocao";
	}

}

package br.edu.ufersa.pefrioshop.web;

import br.edu.ufersa.pefrioshop.service.PedidoService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	private static final int ULTIMOS_PEDIDOS = 5;

	private final PedidoService pedidos;

	public HomeController(PedidoService pedidos) {
		this.pedidos = pedidos;
	}

	@GetMapping("/")
	public String inicio(Model model) {
		List<PedidoService.PedidoComResumo> ultimos = pedidos.listarComResumo().stream()
				.limit(ULTIMOS_PEDIDOS)
				.toList();
		model.addAttribute("ultimosPedidos", ultimos);
		return "index";
	}

}

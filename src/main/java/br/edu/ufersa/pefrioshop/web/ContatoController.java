package br.edu.ufersa.pefrioshop.web;

import br.edu.ufersa.pefrioshop.config.ParametrosLoja;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/** Pagina de contato com o botao de WhatsApp (US 8). */
@Controller
public class ContatoController {

	private final ParametrosLoja parametros;

	public ContatoController(ParametrosLoja parametros) {
		this.parametros = parametros;
	}

	@GetMapping("/contato")
	public String contato(Model model) {
		model.addAttribute("whatsapp", parametros.whatsapp());
		return "contato";
	}

}

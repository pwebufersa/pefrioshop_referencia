package br.edu.ufersa.pefrioshop.web;

import br.edu.ufersa.pefrioshop.domain.Cliente;
import br.edu.ufersa.pefrioshop.domain.FichaClinicaDoPe;
import br.edu.ufersa.pefrioshop.service.ClienteService;
import br.edu.ufersa.pefrioshop.service.FichaClinicaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	private final ClienteService clientes;
	private final FichaClinicaService fichas;

	public ClienteController(ClienteService clientes, FichaClinicaService fichas) {
		this.clientes = clientes;
		this.fichas = fichas;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("clientes", clientes.listar());
		return "cliente/lista";
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "cliente/form";
	}

	@PostMapping
	public String salvar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult resultado) {
		if (resultado.hasErrors()) {
			return "cliente/form";
		}
		clientes.salvar(cliente);
		return "redirect:/clientes";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Long id, Model model) {
		model.addAttribute("cliente", clientes.buscar(id));
		return "cliente/form";
	}

	@PostMapping("/{id}")
	public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("cliente") Cliente cliente,
			BindingResult resultado) {
		cliente.setId(id);
		if (resultado.hasErrors()) {
			return "cliente/form";
		}
		clientes.atualizar(id, cliente);
		return "redirect:/clientes";
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable Long id) {
		clientes.excluir(id);
		return "redirect:/clientes";
	}

	@GetMapping("/{id}/ficha/novo")
	public String novaFicha(@PathVariable Long id, Model model) {
		model.addAttribute("ficha", fichas.novaPara(id));
		model.addAttribute("clienteId", id);
		return "cliente/ficha-form";
	}

	@GetMapping("/{id}/ficha/editar")
	public String editarFicha(@PathVariable Long id, Model model) {
		model.addAttribute("ficha", fichas.buscarDoCliente(id));
		model.addAttribute("clienteId", id);
		return "cliente/ficha-form";
	}

	@PostMapping("/{id}/ficha")
	public String salvarFicha(@PathVariable Long id, @Valid @ModelAttribute("ficha") FichaClinicaDoPe ficha,
			BindingResult resultado, Model model) {
		if (resultado.hasErrors()) {
			model.addAttribute("clienteId", id);
			return "cliente/ficha-form";
		}
		fichas.salvarPara(id, ficha);
		return "redirect:/clientes";
	}

	@PostMapping("/{id}/ficha/excluir")
	public String excluirFicha(@PathVariable Long id) {
		fichas.excluir(id);
		return "redirect:/clientes";
	}

}

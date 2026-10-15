package br.edu.ufersa.pefrioshop.web;

import br.edu.ufersa.pefrioshop.domain.FormaPagamento;
import br.edu.ufersa.pefrioshop.domain.Pedido;
import br.edu.ufersa.pefrioshop.domain.Produto;
import br.edu.ufersa.pefrioshop.service.ClienteService;
import br.edu.ufersa.pefrioshop.service.PedidoService;
import br.edu.ufersa.pefrioshop.service.ProdutoService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	private final PedidoService pedidos;
	private final ClienteService clientes;
	private final ProdutoService produtos;

	public PedidoController(PedidoService pedidos, ClienteService clientes, ProdutoService produtos) {
		this.pedidos = pedidos;
		this.clientes = clientes;
		this.produtos = produtos;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("pedidos", pedidos.listarComResumo());
		return "pedido/lista";
	}

	@GetMapping("/{id}")
	public String detalhe(@PathVariable Long id, Model model) {
		Pedido pedido = pedidos.buscar(id);
		model.addAttribute("pedido", pedido);
		model.addAttribute("resumo", pedidos.resumo(pedido));
		return "pedido/detalhe";
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		List<Produto> lista = produtos.listar();
		model.addAttribute("form", novoForm(lista));
		model.addAttribute("produtos", lista);
		model.addAttribute("clientes", clientes.listar());
		model.addAttribute("formas", FormaPagamento.values());
		return "pedido/form";
	}

	@PostMapping
	public String salvar(@ModelAttribute("form") PedidoForm form) {
		pedidos.registrar(form.getClienteId(), form.selecionados(), form.paraPagamento());
		return "redirect:/pedidos";
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable Long id) {
		pedidos.excluir(id);
		return "redirect:/pedidos";
	}

	/** Os produtos sao exibidos em ordem alfabetica pelo nome (US 4). */
	private PedidoForm novoForm(List<Produto> lista) {
		PedidoForm form = new PedidoForm();
		for (Produto produto : lista) {
			ItemForm item = new ItemForm();
			item.setProdutoId(produto.getId());
			form.getItens().add(item);
		}
		return form;
	}

}

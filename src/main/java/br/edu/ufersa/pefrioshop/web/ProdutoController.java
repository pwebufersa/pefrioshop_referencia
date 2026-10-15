package br.edu.ufersa.pefrioshop.web;

import br.edu.ufersa.pefrioshop.domain.GeneroAlvo;
import br.edu.ufersa.pefrioshop.domain.Produto;
import br.edu.ufersa.pefrioshop.domain.TipoProduto;
import br.edu.ufersa.pefrioshop.service.ProdutoService;
import jakarta.validation.Valid;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	private final ProdutoService produtos;

	public ProdutoController(ProdutoService produtos) {
		this.produtos = produtos;
	}

	@GetMapping
	public String listar(@RequestParam(name = "nome", required = false) String nome, Model model) {
		model.addAttribute("produtos", produtos.buscarPorNome(nome));
		model.addAttribute("nome", nome);
		return "produto/lista";
	}

	@GetMapping("/busca")
	public String buscar(@RequestParam(name = "termo", required = false) String termo,
			@RequestParam(name = "tipo", required = false) TipoProduto tipo,
			@RequestParam(name = "generoAlvo", required = false) GeneroAlvo generoAlvo, Model model) {
		model.addAttribute("produtos", produtos.buscar(termo, tipo, generoAlvo));
		model.addAttribute("termo", termo);
		model.addAttribute("tipoSelecionado", tipo);
		model.addAttribute("generoSelecionado", generoAlvo);
		model.addAttribute("tipos", TipoProduto.values());
		model.addAttribute("generos", GeneroAlvo.values());
		return "produto/busca";
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("produto", new Produto());
		adicionarEnumeracoes(model);
		return "produto/form";
	}

	@PostMapping
	public String salvar(@Valid @ModelAttribute("produto") Produto produto, BindingResult resultado,
			@RequestParam(name = "foto", required = false) MultipartFile foto) throws IOException {
		if (resultado.hasErrors()) {
			return "produto/form";
		}
		aplicarFoto(produto, foto, null);
		produtos.salvar(produto);
		return "redirect:/produtos";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Long id, Model model) {
		model.addAttribute("produto", produtos.buscar(id));
		adicionarEnumeracoes(model);
		return "produto/form";
	}

	@PostMapping("/{id}")
	public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("produto") Produto produto,
			BindingResult resultado, @RequestParam(name = "foto", required = false) MultipartFile foto)
			throws IOException {
		produto.setId(id);
		if (resultado.hasErrors()) {
			return "produto/form";
		}
		aplicarFoto(produto, foto, produtos.buscar(id));
		produtos.salvar(produto);
		return "redirect:/produtos";
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable Long id) {
		produtos.excluir(id);
		return "redirect:/produtos";
	}

	@GetMapping("/{id}/foto")
	public ResponseEntity<byte[]> foto(@PathVariable Long id) {
		byte[] conteudo = produtos.foto(id);
		if (conteudo == null || conteudo.length == 0) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(produtos.fotoContentType(id)))
				.body(conteudo);
	}

	private void aplicarFoto(Produto produto, MultipartFile foto, Produto atual) throws IOException {
		if (foto != null && !foto.isEmpty()) {
			produto.setFoto(foto.getBytes());
			produto.setFotoContentType(foto.getContentType());
			return;
		}
		if (atual != null) {
			produto.setFoto(atual.getFoto());
			produto.setFotoContentType(atual.getFotoContentType());
		}
	}

	private void adicionarEnumeracoes(Model model) {
		model.addAttribute("tipos", TipoProduto.values());
		model.addAttribute("generos", GeneroAlvo.values());
	}

}

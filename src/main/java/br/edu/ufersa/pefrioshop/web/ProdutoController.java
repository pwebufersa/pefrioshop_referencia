package br.edu.ufersa.pefrioshop.web;

import br.edu.ufersa.pefrioshop.domain.GeneroAlvo;
import br.edu.ufersa.pefrioshop.domain.Produto;
import br.edu.ufersa.pefrioshop.domain.TipoProduto;
import br.edu.ufersa.pefrioshop.service.ProdutoService;
import jakarta.validation.Valid;
import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	private static final String MARCADOR = "static/imagens/sem-foto.svg";
	private static final MediaType TIPO_SVG = MediaType.valueOf("image/svg+xml");

	private final ProdutoService produtos;

	public ProdutoController(ProdutoService produtos) {
		this.produtos = produtos;
	}

	/** A foto e tratada pelo arquivo enviado, nunca por ligacao direta. */
	@InitBinder("produto")
	public void proibirLigacaoDaFoto(WebDataBinder binder) {
		binder.setDisallowedFields("foto");
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
		return "produto/form";
	}

	@PostMapping
	public String salvar(@Valid @ModelAttribute("produto") Produto produto, BindingResult resultado,
			@RequestParam(name = "arquivo", required = false) MultipartFile arquivo) throws IOException {
		if (resultado.hasErrors()) {
			return "produto/form";
		}
		produtos.salvar(produto, conteudoDaFoto(arquivo), tipoDaFoto(arquivo));
		return "redirect:/produtos";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Long id, Model model) {
		model.addAttribute("produto", produtos.buscar(id));
		return "produto/form";
	}

	@PostMapping("/{id}")
	public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("produto") Produto produto,
			BindingResult resultado, @RequestParam(name = "arquivo", required = false) MultipartFile arquivo)
			throws IOException {
		produto.setId(id);
		if (resultado.hasErrors()) {
			return "produto/form";
		}
		produtos.atualizar(id, produto, conteudoDaFoto(arquivo), tipoDaFoto(arquivo));
		return "redirect:/produtos";
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable Long id) {
		produtos.excluir(id);
		return "redirect:/produtos";
	}

	/** Entrega a imagem original (US 12). */
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

	/** Entrega a miniatura; sem foto, entrega o marcador. */
	@GetMapping("/{id}/foto/miniatura")
	public ResponseEntity<byte[]> miniatura(@PathVariable Long id) {
		byte[] conteudo = produtos.miniatura(id);
		if (conteudo == null || conteudo.length == 0) {
			return ResponseEntity.ok().contentType(TIPO_SVG).body(marcador());
		}
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(conteudo);
	}

	private byte[] conteudoDaFoto(MultipartFile foto) throws IOException {
		return foto == null || foto.isEmpty() ? null : foto.getBytes();
	}

	private String tipoDaFoto(MultipartFile foto) {
		return foto == null || foto.isEmpty() ? null : foto.getContentType();
	}

	private byte[] marcador() {
		try {
			return new ClassPathResource(MARCADOR).getContentAsByteArray();
		} catch (IOException excecao) {
			return new byte[0];
		}
	}

}

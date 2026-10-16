package br.edu.ufersa.pefrioshop.service;

import br.edu.ufersa.pefrioshop.domain.GeneroAlvo;
import br.edu.ufersa.pefrioshop.domain.Produto;
import br.edu.ufersa.pefrioshop.domain.ProdutoFoto;
import br.edu.ufersa.pefrioshop.domain.TipoProduto;
import br.edu.ufersa.pefrioshop.repository.ContagemPorTipo;
import br.edu.ufersa.pefrioshop.repository.ProdutoRepository;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

	private static final String TIPO_PADRAO = "application/octet-stream";
	private static final String TIPO_MINIATURA = "image/png";

	private final ProdutoRepository produtos;
	private final GeradorDeMiniatura miniaturas;

	public ProdutoService(ProdutoRepository produtos, GeradorDeMiniatura miniaturas) {
		this.produtos = produtos;
		this.miniaturas = miniaturas;
	}

	public List<Produto> listar() {
		return produtos.findAll(Sort.by("nome"));
	}

	public Produto buscar(Long id) {
		return produtos.findById(id).orElseThrow(() -> new IllegalArgumentException(
				"Produto não encontrado: id=" + id + ". Verifique a lista de produtos."));
	}

	@Transactional
	public Produto salvar(Produto produto, byte[] conteudo, String contentType) {
		aplicarFoto(produto, conteudo, contentType);
		return produtos.save(produto);
	}

	/**
	 * Atualiza os campos editaveis do produto. A data de cadastro nao e tocada, e
	 * a foto so e substituida quando um arquivo novo chega.
	 */
	@Transactional
	public Produto atualizar(Long id, Produto dados, byte[] conteudo, String contentType) {
		Produto produto = buscar(id);
		produto.setNome(dados.getNome());
		produto.setMarca(dados.getMarca());
		produto.setTipo(dados.getTipo());
		produto.setGeneroAlvo(dados.getGeneroAlvo());
		produto.setNumeroBr(dados.getNumeroBr());
		produto.setPrecoCompra(dados.getPrecoCompra());
		produto.setPrecoVenda(dados.getPrecoVenda());
		aplicarFoto(produto, conteudo, contentType);
		return produtos.save(produto);
	}

	@Transactional
	public void excluir(Long id) {
		Produto produto = buscar(id);
		try {
			produtos.delete(produto);
			produtos.flush();
		} catch (DataIntegrityViolationException excecao) {
			throw new IllegalArgumentException("O produto " + produto.getNome() + " (id=" + id
					+ ") aparece em um ou mais pedidos e não pode ser excluído. Exclua os pedidos primeiro.");
		}
	}

	/** Busca pelo nome (US 6). */
	public List<Produto> buscarPorNome(String nome) {
		if (nome == null || nome.isBlank()) {
			return listar();
		}
		return produtos.findByNomeContainingIgnoreCaseOrderByNomeAsc(nome.trim());
	}

	/** Busca por qualquer campo combinado com filtros (US 7). */
	public List<Produto> buscar(String termo, TipoProduto tipo, GeneroAlvo generoAlvo) {
		Specification<Produto> filtro = (root, query, cb) -> cb.conjunction();
		if (termo != null && !termo.isBlank()) {
			String alvo = "%" + termo.trim().toLowerCase() + "%";
			filtro = filtro.and((root, query, cb) -> cb.or(
					cb.like(cb.lower(root.get("nome")), alvo),
					cb.like(cb.lower(root.get("marca")), alvo)));
		}
		if (tipo != null) {
			filtro = filtro.and((root, query, cb) -> cb.equal(root.get("tipo"), tipo));
		}
		if (generoAlvo != null) {
			filtro = filtro.and((root, query, cb) -> cb.equal(root.get("generoAlvo"), generoAlvo));
		}
		return produtos.findAll(filtro, Sort.by("nome"));
	}

	public List<ContagemPorTipo> contagemPorTipo() {
		return produtos.contarPorTipo();
	}

	@Transactional(readOnly = true)
	public byte[] foto(Long id) {
		ProdutoFoto foto = fotoDe(id);
		return foto == null ? null : foto.getConteudo();
	}

	@Transactional(readOnly = true)
	public String fotoContentType(Long id) {
		ProdutoFoto foto = fotoDe(id);
		return foto == null || foto.getContentType() == null ? TIPO_PADRAO : foto.getContentType();
	}

	@Transactional(readOnly = true)
	public byte[] miniatura(Long id) {
		ProdutoFoto foto = fotoDe(id);
		return foto == null ? null : foto.getMiniatura();
	}

	private ProdutoFoto fotoDe(Long id) {
		return buscar(id).getFoto();
	}

	private void aplicarFoto(Produto produto, byte[] conteudo, String contentType) {
		if (conteudo == null || conteudo.length == 0) {
			return;
		}
		ProdutoFoto foto = produto.getFoto();
		if (foto == null) {
			foto = new ProdutoFoto();
			foto.setProduto(produto);
			produto.setFoto(foto);
		}
		foto.setConteudo(conteudo);
		foto.setContentType(contentType);
		byte[] miniatura = miniaturas.gerar(conteudo);
		foto.setMiniatura(miniatura);
		foto.setMiniaturaContentType(miniatura == null ? null : TIPO_MINIATURA);
	}

}

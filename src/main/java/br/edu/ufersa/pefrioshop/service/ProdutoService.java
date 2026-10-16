package br.edu.ufersa.pefrioshop.service;

import br.edu.ufersa.pefrioshop.domain.GeneroAlvo;
import br.edu.ufersa.pefrioshop.domain.Produto;
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

	private final ProdutoRepository produtos;

	public ProdutoService(ProdutoRepository produtos) {
		this.produtos = produtos;
	}

	public List<Produto> listar() {
		return produtos.findAll(Sort.by("nome"));
	}

	public Produto buscar(Long id) {
		return produtos.findById(id).orElseThrow(() -> new IllegalArgumentException(
				"Produto não encontrado: id=" + id + ". Verifique a lista de produtos."));
	}

	public Produto salvar(Produto produto) {
		return produtos.save(produto);
	}

	/**
	 * Atualiza os campos editaveis do produto. A data de cadastro nao e tocada, e
	 * a foto so e substituida quando um arquivo novo chega.
	 */
	@Transactional
	public Produto atualizar(Long id, Produto dados, byte[] foto, String fotoContentType) {
		Produto produto = buscar(id);
		produto.setNome(dados.getNome());
		produto.setMarca(dados.getMarca());
		produto.setTipo(dados.getTipo());
		produto.setGeneroAlvo(dados.getGeneroAlvo());
		produto.setNumeroBr(dados.getNumeroBr());
		produto.setPrecoCompra(dados.getPrecoCompra());
		produto.setPrecoVenda(dados.getPrecoVenda());
		if (foto != null && foto.length > 0) {
			produto.setFoto(foto);
			produto.setFotoContentType(fotoContentType);
		}
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

	@Transactional(readOnly = true)
	public byte[] foto(Long id) {
		return buscar(id).getFoto();
	}

	@Transactional(readOnly = true)
	public String fotoContentType(Long id) {
		String tipo = buscar(id).getFotoContentType();
		return tipo == null ? "application/octet-stream" : tipo;
	}

	public List<ContagemPorTipo> contagemPorTipo() {
		return produtos.contarPorTipo();
	}

}

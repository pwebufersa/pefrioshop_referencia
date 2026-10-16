package br.edu.ufersa.pefrioshop.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;

/**
 * Foto do produto, guardada em tabela própria.
 *
 * <p>A separação existe para a linha do produto ficar leve: a listagem lê
 * apenas as colunas do produto, sem trazer os bytes da imagem.
 *
 * <p>A miniatura fica ao lado da imagem grande, para a listagem não baixar o
 * arquivo original.
 */
@Entity
public class ProdutoFoto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "produto_id", nullable = false, unique = true)
	private Produto produto;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(nullable = false)
	private byte[] conteudo;

	@Column(nullable = false, length = 60)
	private String contentType;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] miniatura;

	@Column(length = 60)
	private String miniaturaContentType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public byte[] getConteudo() {
		return conteudo;
	}

	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getMiniatura() {
		return miniatura;
	}

	public void setMiniatura(byte[] miniatura) {
		this.miniatura = miniatura;
	}

	public String getMiniaturaContentType() {
		return miniaturaContentType;
	}

	public void setMiniaturaContentType(String miniaturaContentType) {
		this.miniaturaContentType = miniaturaContentType;
	}

}

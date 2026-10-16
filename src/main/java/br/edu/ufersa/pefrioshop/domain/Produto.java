package br.edu.ufersa.pefrioshop.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O nome do produto é obrigatório.")
	@Column(nullable = false, length = 120)
	private String nome;

	@NotBlank(message = "A marca é obrigatória.")
	@Column(nullable = false, length = 80)
	private String marca;

	@NotNull(message = "O tipo é obrigatório.")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 12)
	private TipoProduto tipo;

	@NotNull(message = "O gênero alvo é obrigatório.")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 12)
	private GeneroAlvo generoAlvo;

	@NotNull(message = "O número BR é obrigatório.")
	@Positive(message = "O número BR deve ser maior que zero.")
	@Column(nullable = false)
	private Integer numeroBr;

	@NotNull(message = "O preço de compra é obrigatório.")
	@PositiveOrZero(message = "O preço de compra não pode ser negativo.")
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal precoCompra;

	@NotNull(message = "O preço de venda é obrigatório.")
	@Positive(message = "O preço de venda deve ser maior que zero.")
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal precoVenda;

	// Definida pelo sistema, nao pelo formulario.
	@Column(nullable = false, updatable = false)
	private LocalDate dataCadastro;

	// A foto fica em tabela propria. A linha do produto nao carrega os bytes.
	@OneToOne(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private ProdutoFoto foto;

	@PrePersist
	void definirDataCadastro() {
		if (dataCadastro == null) {
			dataCadastro = LocalDate.now();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public TipoProduto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProduto tipo) {
		this.tipo = tipo;
	}

	public GeneroAlvo getGeneroAlvo() {
		return generoAlvo;
	}

	public void setGeneroAlvo(GeneroAlvo generoAlvo) {
		this.generoAlvo = generoAlvo;
	}

	public Integer getNumeroBr() {
		return numeroBr;
	}

	public void setNumeroBr(Integer numeroBr) {
		this.numeroBr = numeroBr;
	}

	public BigDecimal getPrecoCompra() {
		return precoCompra;
	}

	public void setPrecoCompra(BigDecimal precoCompra) {
		this.precoCompra = precoCompra;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public ProdutoFoto getFoto() {
		return foto;
	}

	public void setFoto(ProdutoFoto foto) {
		this.foto = foto;
	}

}

package br.edu.ufersa.pefrioshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Endereço do cliente, em tabela própria.
 *
 * <p>A tabela tem identidade, e o endereço pode crescer (país, complemento,
 * referência) sem inchar a linha do cliente.
 */
@Entity
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id", nullable = false, unique = true)
	private Cliente cliente;

	@NotBlank(message = "A rua é obrigatória.")
	@Column(nullable = false, length = 120)
	private String rua;

	@Column(length = 80)
	private String bairro;

	@NotBlank(message = "O número é obrigatório.")
	@Column(nullable = false, length = 10)
	private String numero;

	@NotBlank(message = "A cidade é obrigatória.")
	@Column(nullable = false, length = 80)
	private String cidade;

	@NotBlank(message = "O CEP é obrigatório.")
	@Pattern(regexp = "[0-9A-Za-z-]{3,10}",
			message = "O CEP informado é inválido: use de 3 a 10 caracteres, como 59650000 ou 1100-053.")
	@Column(nullable = false, length = 10)
	private String cep;

	@NotNull(message = "O país é obrigatório.")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Pais pais = Pais.BRASIL;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

}

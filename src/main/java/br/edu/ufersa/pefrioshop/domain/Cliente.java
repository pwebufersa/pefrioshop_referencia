package br.edu.ufersa.pefrioshop.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O nome completo é obrigatório.")
	@Column(nullable = false, length = 120)
	private String nomeCompleto;

	@NotNull(message = "O gênero é obrigatório.")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Genero genero;

	@Email(message = "O e-mail informado é inválido.")
	@Column(length = 120)
	private String email;

	@NotBlank(message = "O telefone com DDD é obrigatório.")
	@Column(nullable = false, length = 15)
	private String telefone;

	@NotNull(message = "A data de nascimento é obrigatória.")
	private LocalDate dataNascimento;

	// Definida pelo sistema, nao pelo formulario.
	@Column(nullable = false, updatable = false)
	private LocalDateTime dataCadastro;

	@Embedded
	private Endereco endereco = new Endereco();

	// A ficha pertence ao cliente: excluir o cliente exclui a ficha em cascata.
	@OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private FichaClinicaDoPe fichaClinica;

	@PrePersist
	void definirDataCadastro() {
		if (dataCadastro == null) {
			dataCadastro = LocalDateTime.now();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public FichaClinicaDoPe getFichaClinica() {
		return fichaClinica;
	}

	public void setFichaClinica(FichaClinicaDoPe fichaClinica) {
		this.fichaClinica = fichaClinica;
	}

}

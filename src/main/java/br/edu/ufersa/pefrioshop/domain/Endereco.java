package br.edu.ufersa.pefrioshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Endereco do cliente. E um objeto de valor, por isso nao tem identidade
 * propria: fica embutido na tabela do cliente.
 */
@Embeddable
public class Endereco {

	@Column(length = 120)
	private String rua;

	@Column(length = 80)
	private String bairro;

	@Column(length = 10)
	private String numero;

	@Column(length = 80)
	private String cidade;

	@Column(length = 9)
	private String cep;

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

}

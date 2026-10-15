package br.edu.ufersa.pefrioshop.domain;

public enum FormaPagamento {

	DINHEIRO("Dinheiro"),
	CARTAO_CREDITO("Cartão de Crédito"),
	PIX("Pix");

	private final String rotulo;

	FormaPagamento(String rotulo) {
		this.rotulo = rotulo;
	}

	public String getRotulo() {
		return rotulo;
	}

}

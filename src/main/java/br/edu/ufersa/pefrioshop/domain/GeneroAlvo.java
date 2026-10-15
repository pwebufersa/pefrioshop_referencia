package br.edu.ufersa.pefrioshop.domain;

public enum GeneroAlvo {

	MASCULINO("Masculino"),
	FEMININO("Feminino"),
	UNISSEX("Unissex"),
	INFANTIL("Infantil");

	private final String rotulo;

	GeneroAlvo(String rotulo) {
		this.rotulo = rotulo;
	}

	public String getRotulo() {
		return rotulo;
	}

}

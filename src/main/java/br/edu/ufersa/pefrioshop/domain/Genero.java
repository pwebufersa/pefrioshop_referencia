package br.edu.ufersa.pefrioshop.domain;

public enum Genero {

	MASCULINO("Masculino"),
	FEMININO("Feminino"),
	NAO_INFORMADO("Não Informado");

	private final String rotulo;

	Genero(String rotulo) {
		this.rotulo = rotulo;
	}

	public String getRotulo() {
		return rotulo;
	}

}

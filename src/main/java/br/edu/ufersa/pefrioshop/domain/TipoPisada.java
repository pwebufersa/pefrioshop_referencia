package br.edu.ufersa.pefrioshop.domain;

public enum TipoPisada {

	PRONADA("Pronada"),
	SUPINADA("Supinada"),
	NEUTRA("Neutra");

	private final String rotulo;

	TipoPisada(String rotulo) {
		this.rotulo = rotulo;
	}

	public String getRotulo() {
		return rotulo;
	}

}

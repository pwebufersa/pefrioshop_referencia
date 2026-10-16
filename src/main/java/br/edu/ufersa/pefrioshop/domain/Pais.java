package br.edu.ufersa.pefrioshop.domain;

public enum Pais {

	BRASIL("Brasil"),
	PORTUGAL("Portugal"),
	ESTADOS_UNIDOS("Estados Unidos"),
	ESPANHA("Espanha"),
	ARGENTINA("Argentina"),
	OUTRO("Outro");

	private final String rotulo;

	Pais(String rotulo) {
		this.rotulo = rotulo;
	}

	public String getRotulo() {
		return rotulo;
	}

}

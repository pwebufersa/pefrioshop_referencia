package br.edu.ufersa.pefrioshop.domain;

public enum TemperaturaPe {

	FRIO("Frio"),
	NORMAL("Normal"),
	QUENTE("Quente");

	private final String rotulo;

	TemperaturaPe(String rotulo) {
		this.rotulo = rotulo;
	}

	public String getRotulo() {
		return rotulo;
	}

}

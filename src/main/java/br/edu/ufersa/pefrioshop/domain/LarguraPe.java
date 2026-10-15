package br.edu.ufersa.pefrioshop.domain;

public enum LarguraPe {

	ESTREITO("Estreito"),
	NORMAL("Normal"),
	LARGO("Largo");

	private final String rotulo;

	LarguraPe(String rotulo) {
		this.rotulo = rotulo;
	}

	public String getRotulo() {
		return rotulo;
	}

}

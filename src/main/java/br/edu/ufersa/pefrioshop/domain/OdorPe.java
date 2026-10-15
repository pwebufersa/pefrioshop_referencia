package br.edu.ufersa.pefrioshop.domain;

public enum OdorPe {

	SEM_ODOR("Sem Odor"),
	LEVE("Leve"),
	FORTE("Forte");

	private final String rotulo;

	OdorPe(String rotulo) {
		this.rotulo = rotulo;
	}

	public String getRotulo() {
		return rotulo;
	}

}

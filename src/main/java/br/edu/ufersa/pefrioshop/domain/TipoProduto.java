package br.edu.ufersa.pefrioshop.domain;

public enum TipoProduto {

	TENIS("Tênis"),
	SANDALIA("Sandália"),
	BOTA("Bota"),
	SAPATENIS("Sapatênis"),
	CHINELO("Chinelo");

	private final String rotulo;

	TipoProduto(String rotulo) {
		this.rotulo = rotulo;
	}

	public String getRotulo() {
		return rotulo;
	}

}

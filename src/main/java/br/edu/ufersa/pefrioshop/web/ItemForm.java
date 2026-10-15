package br.edu.ufersa.pefrioshop.web;

/** Linha do formulario de pedido: um produto e a quantidade desejada. */
public class ItemForm {

	private Long produtoId;
	private boolean incluir;
	private int quantidade = 1;

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public boolean isIncluir() {
		return incluir;
	}

	public void setIncluir(boolean incluir) {
		this.incluir = incluir;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}

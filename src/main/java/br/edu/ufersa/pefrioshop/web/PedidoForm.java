package br.edu.ufersa.pefrioshop.web;

import br.edu.ufersa.pefrioshop.domain.FormaPagamento;
import br.edu.ufersa.pefrioshop.domain.PagamentoDoPedido;
import br.edu.ufersa.pefrioshop.service.ItemSolicitado;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Dados do formulario de pedido. */
public class PedidoForm {

	private Long clienteId;
	private FormaPagamento forma;
	private String cartaoUltimosDigitos;
	private String chavePix;
	private BigDecimal valorPago;
	private List<ItemForm> itens = new ArrayList<>();

	public List<ItemSolicitado> selecionados() {
		return itens.stream()
				.filter(ItemForm::isIncluir)
				.map(item -> new ItemSolicitado(item.getProdutoId(), item.getQuantidade()))
				.toList();
	}

	public PagamentoDoPedido paraPagamento() {
		PagamentoDoPedido pagamento = new PagamentoDoPedido();
		pagamento.setForma(forma);
		pagamento.setCartaoUltimosDigitos(cartaoUltimosDigitos);
		pagamento.setChavePix(chavePix);
		pagamento.setValorPago(valorPago);
		return pagamento;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public FormaPagamento getForma() {
		return forma;
	}

	public void setForma(FormaPagamento forma) {
		this.forma = forma;
	}

	public String getCartaoUltimosDigitos() {
		return cartaoUltimosDigitos;
	}

	public void setCartaoUltimosDigitos(String cartaoUltimosDigitos) {
		this.cartaoUltimosDigitos = cartaoUltimosDigitos;
	}

	public String getChavePix() {
		return chavePix;
	}

	public void setChavePix(String chavePix) {
		this.chavePix = chavePix;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public List<ItemForm> getItens() {
		return itens;
	}

	public void setItens(List<ItemForm> itens) {
		this.itens = itens;
	}

}

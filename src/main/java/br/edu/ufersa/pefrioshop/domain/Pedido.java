package br.edu.ufersa.pefrioshop.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "O cliente é obrigatório.")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	// Definida pelo sistema, nao pelo formulario.
	@Column(nullable = false, updatable = false)
	private LocalDateTime dataPedido;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemPedido> itens = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "pagamento_id", nullable = false)
	private PagamentoDoPedido pagamento;

	@PrePersist
	void definirDataPedido() {
		if (dataPedido == null) {
			dataPedido = LocalDateTime.now();
		}
	}

	/** Soma dos subtotais, sem imposto. */
	public BigDecimal getValorTotal() {
		return itens.stream()
				.map(ItemPedido::getSubtotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void adicionarItem(ItemPedido item) {
		item.setPedido(this);
		itens.add(item);
	}

	public void limparItens() {
		itens.clear();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	public PagamentoDoPedido getPagamento() {
		return pagamento;
	}

	public void setPagamento(PagamentoDoPedido pagamento) {
		this.pagamento = pagamento;
	}

}

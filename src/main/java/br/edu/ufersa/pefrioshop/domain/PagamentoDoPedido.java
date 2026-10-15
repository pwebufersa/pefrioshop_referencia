package br.edu.ufersa.pefrioshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Dados da forma de pagamento do pedido.
 *
 * <p>Por seguranca, apenas os quatro ultimos digitos do cartao sao guardados.
 * O numero completo nunca e persistido.
 */
@Entity
public class PagamentoDoPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "A forma de pagamento é obrigatória.")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private FormaPagamento forma;

	@Column(length = 4)
	private String cartaoUltimosDigitos;

	@Column(length = 120)
	private String chavePix;

	@Column(precision = 12, scale = 2)
	private BigDecimal valorPago;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}

package br.edu.ufersa.pefrioshop.service;

import br.edu.ufersa.pefrioshop.config.ParametrosLoja;
import br.edu.ufersa.pefrioshop.domain.Cliente;
import br.edu.ufersa.pefrioshop.domain.ItemPedido;
import br.edu.ufersa.pefrioshop.domain.PagamentoDoPedido;
import br.edu.ufersa.pefrioshop.domain.Pedido;
import br.edu.ufersa.pefrioshop.domain.Produto;
import br.edu.ufersa.pefrioshop.repository.ClienteRepository;
import br.edu.ufersa.pefrioshop.repository.PedidoRepository;
import br.edu.ufersa.pefrioshop.repository.ProdutoRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

	private final PedidoRepository pedidos;
	private final ProdutoRepository produtos;
	private final ClienteRepository clientes;
	private final ParametrosLoja parametros;

	public PedidoService(PedidoRepository pedidos, ProdutoRepository produtos, ClienteRepository clientes,
			ParametrosLoja parametros) {
		this.pedidos = pedidos;
		this.produtos = produtos;
		this.clientes = clientes;
		this.parametros = parametros;
	}

	public List<Pedido> listar() {
		return pedidos.findAllByOrderByDataPedidoDesc();
	}

	public Pedido buscar(Long id) {
		return pedidos.findById(id).orElseThrow(() -> new IllegalArgumentException(
				"Pedido não encontrado: id=" + id + ". Verifique a lista de pedidos."));
	}

	@Transactional
	public void excluir(Long id) {
		pedidos.delete(buscar(id));
	}

	/**
	 * Monta e grava o pedido. O valor unitario de cada item e copiado do produto
	 * no momento da compra, para preservar o historico.
	 */
	@Transactional
	public Pedido registrar(Long clienteId, List<ItemSolicitado> itens, PagamentoDoPedido pagamento) {
		if (itens == null || itens.isEmpty()) {
			throw new IllegalArgumentException(
					"O pedido precisa de pelo menos um item. Selecione ao menos um produto.");
		}
		Cliente cliente = clientes.findById(clienteId).orElseThrow(() -> new IllegalArgumentException(
				"Cliente não encontrado: id=" + clienteId + ". Verifique a lista de clientes."));
		validarPagamento(pagamento);

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setPagamento(pagamento);

		for (ItemSolicitado solicitado : itens) {
			validarQuantidade(solicitado.quantidade());
			Produto produto = produtos.findById(solicitado.produtoId()).orElseThrow(
					() -> new IllegalArgumentException("Produto não encontrado: id=" + solicitado.produtoId()
							+ ". Verifique a lista de produtos."));
			ItemPedido item = new ItemPedido();
			item.setProduto(produto);
			item.setQuantidade(solicitado.quantidade());
			item.setValorUnitario(produto.getPrecoVenda());
			pedido.adicionarItem(item);
		}
		return pedidos.save(pedido);
	}

	/** Totais do pedido: valor, imposto e valor com imposto. */
	public ResumoPedido resumo(Pedido pedido) {
		BigDecimal total = pedido.getValorTotal();
		BigDecimal imposto = total.multiply(parametros.imposto()).setScale(2, RoundingMode.HALF_UP);
		return new ResumoPedido(total, imposto, total.add(imposto));
	}

	/** Pedido com os totais prontos para a tela. */
	public record PedidoComResumo(Pedido pedido, ResumoPedido resumo) {
	}

	public List<PedidoComResumo> listarComResumo() {
		return listar().stream().map(pedido -> new PedidoComResumo(pedido, resumo(pedido))).toList();
	}

	private void validarQuantidade(int quantidade) {
		if (quantidade <= 0) {
			throw new IllegalArgumentException("Quantidade inválida: " + quantidade
					+ ". O formato esperado é um inteiro maior que zero.");
		}
	}

	private void validarPagamento(PagamentoDoPedido pagamento) {
		if (pagamento == null || pagamento.getForma() == null) {
			throw new IllegalArgumentException(
					"A forma de pagamento é obrigatória. Escolha Dinheiro, Cartão de Crédito ou Pix.");
		}
		switch (pagamento.getForma()) {
			case CARTAO_CREDITO -> {
				String digitos = pagamento.getCartaoUltimosDigitos();
				if (digitos == null || !digitos.matches("\\d{4}")) {
					throw new IllegalArgumentException("Cartão inválido: " + digitos
							+ ". Informe os 4 últimos dígitos (ex.: 1234).");
				}
			}
			case PIX -> {
				if (pagamento.getChavePix() == null || pagamento.getChavePix().isBlank()) {
					throw new IllegalArgumentException(
							"A chave Pix é obrigatória quando a forma de pagamento é Pix.");
				}
			}
			case DINHEIRO -> {
				if (pagamento.getValorPago() == null || pagamento.getValorPago().signum() <= 0) {
					throw new IllegalArgumentException("Valor pago inválido: " + pagamento.getValorPago()
							+ ". Informe um valor maior que zero.");
				}
			}
		}
	}

}

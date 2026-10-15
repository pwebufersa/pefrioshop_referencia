package br.edu.ufersa.pefrioshop.service;

import br.edu.ufersa.pefrioshop.config.ParametrosLoja;
import br.edu.ufersa.pefrioshop.domain.Cliente;
import br.edu.ufersa.pefrioshop.domain.ItemPedido;
import br.edu.ufersa.pefrioshop.domain.Pedido;
import br.edu.ufersa.pefrioshop.domain.Produto;
import br.edu.ufersa.pefrioshop.domain.TipoProduto;
import br.edu.ufersa.pefrioshop.repository.PedidoRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * E-mail promocional (US 9).
 *
 * <p>Monta a oferta de 5% no tenis mais caro do ultimo pedido, 30 dias depois
 * da compra. O envio real depende de um servidor SMTP; nesta referencia a
 * mensagem e registrada no log e devolvida para a tela. Para enviar de fato,
 * basta injetar um JavaMailSender e trocar o registro do log.
 */
@Service
public class PromocaoService {

	private static final Logger LOG = LoggerFactory.getLogger(PromocaoService.class);
	private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

	private final PedidoRepository pedidos;
	private final ParametrosLoja parametros;

	public PromocaoService(PedidoRepository pedidos, ParametrosLoja parametros) {
		this.pedidos = pedidos;
		this.parametros = parametros;
	}

	public Optional<PromocaoEmail> gerar(Long clienteId) {
		return gerar(clienteId, LocalDateTime.now());
	}

	@Transactional(readOnly = true)
	public Optional<PromocaoEmail> gerar(Long clienteId, LocalDateTime agora) {
		Optional<Pedido> ultimo = pedidos.findFirstByClienteIdOrderByDataPedidoDesc(clienteId);
		if (ultimo.isEmpty()) {
			return Optional.empty();
		}
		Pedido pedido = ultimo.get();
		LocalDateTime elegivelEm = pedido.getDataPedido().plusDays(parametros.diasEmailPromocional());
		if (agora.isBefore(elegivelEm)) {
			return Optional.empty();
		}
		Optional<Produto> tenisMaisCaro = pedido.getItens().stream()
				.map(ItemPedido::getProduto)
				.filter(produto -> produto.getTipo() == TipoProduto.TENIS)
				.max(Comparator.comparing(Produto::getPrecoVenda));
		if (tenisMaisCaro.isEmpty()) {
			return Optional.empty();
		}

		Produto produto = tenisMaisCaro.get();
		BigDecimal comDesconto = produto.getPrecoVenda()
				.multiply(BigDecimal.ONE.subtract(parametros.descontoEmail()))
				.setScale(2, RoundingMode.HALF_UP);
		Cliente cliente = pedido.getCliente();
		String assunto = "PéFrioShop: 5% de desconto no seu tênis";
		String corpo = "Olá, " + cliente.getNomeCompleto() + "!\n\n"
				+ "Notamos que você comprou o " + produto.getNome() + " (" + produto.getMarca() + ").\n"
				+ "Como já passaram " + parametros.diasEmailPromocional() + " dias, oferecemos 5% de desconto "
				+ "na próxima compra desse tênis.\n"
				+ "De " + formatar(produto.getPrecoVenda()) + " por " + formatar(comDesconto) + ".\n\n"
				+ "PéFrioShop - o pé agradece, mesmo no frio.";

		LOG.info("E-mail promocional preparado para {}: {}", cliente.getEmail(), assunto);
		return Optional.of(new PromocaoEmail(cliente, assunto, corpo));
	}

	private String formatar(BigDecimal valor) {
		return NumberFormat.getCurrencyInstance(PT_BR).format(valor);
	}

}

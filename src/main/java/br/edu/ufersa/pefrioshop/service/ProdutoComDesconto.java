package br.edu.ufersa.pefrioshop.service;

import br.edu.ufersa.pefrioshop.domain.Produto;
import java.math.BigDecimal;

/** Produto com o preco ja descontado (US 14). */
public record ProdutoComDesconto(Produto produto, BigDecimal precoComDesconto) {
}

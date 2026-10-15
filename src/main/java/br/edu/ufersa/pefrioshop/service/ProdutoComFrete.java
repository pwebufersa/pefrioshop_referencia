package br.edu.ufersa.pefrioshop.service;

import br.edu.ufersa.pefrioshop.domain.Produto;
import java.math.BigDecimal;

/** Produto com o frete estimado para Marte (US 13). */
public record ProdutoComFrete(Produto produto, BigDecimal frete) {
}

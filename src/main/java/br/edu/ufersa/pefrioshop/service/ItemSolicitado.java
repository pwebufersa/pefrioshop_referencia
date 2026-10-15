package br.edu.ufersa.pefrioshop.service;

/**
 * Produto desejado e quantidade, ja resolvidos a partir do formulario de pedido.
 */
public record ItemSolicitado(Long produtoId, int quantidade) {
}

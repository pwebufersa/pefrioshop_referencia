package br.edu.ufersa.pefrioshop.service;

import br.edu.ufersa.pefrioshop.domain.Cliente;

/** Cliente elegivel e o e-mail promocional montado (US 9). */
public record PromocaoEmail(Cliente cliente, String assunto, String corpo) {
}

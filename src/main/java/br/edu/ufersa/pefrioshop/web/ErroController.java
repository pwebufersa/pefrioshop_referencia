package br.edu.ufersa.pefrioshop.web;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Converte erros de regra de negocio em uma pagina com a mensagem, em vez de
 * uma pilha de excecao na tela.
 */
@ControllerAdvice
public class ErroController {

	@ExceptionHandler(IllegalArgumentException.class)
	public String tratar(IllegalArgumentException excecao, Model model) {
		model.addAttribute("mensagem", excecao.getMessage());
		return "erro";
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public String tratarIntegridade(DataIntegrityViolationException excecao, Model model) {
		model.addAttribute("mensagem",
				"O registro é usado por outro registro e não pode ser excluído. "
						+ "Verifique os pedidos antes de excluir.");
		return "erro";
	}

}

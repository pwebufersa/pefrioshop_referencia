package br.edu.ufersa.pefrioshop.web;

import br.edu.ufersa.pefrioshop.domain.Endereco;
import br.edu.ufersa.pefrioshop.service.CepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint que devolve o endereco de um CEP (US 10). A pagina chama este
 * endpoint e preenche o formulario de cliente.
 */
@RestController
@RequestMapping("/api/cep")
public class CepController {

	private final CepService ceps;

	public CepController(CepService ceps) {
		this.ceps = ceps;
	}

	@GetMapping("/{cep}")
	public ResponseEntity<Object> buscar(@PathVariable String cep) {
		try {
			return ceps.buscar(cep)
					.<ResponseEntity<Object>>map(ResponseEntity::ok)
					.orElseGet(() -> ResponseEntity.notFound().build());
		} catch (IllegalArgumentException excecao) {
			return ResponseEntity.badRequest().body(excecao.getMessage());
		}
	}

}

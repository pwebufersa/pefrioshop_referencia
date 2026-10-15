package br.edu.ufersa.pefrioshop.service;

import br.edu.ufersa.pefrioshop.domain.Endereco;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

/**
 * Preenchimento do endereco pelo CEP (US 10).
 *
 * <p>Consulta a BrasilAPI no servidor e devolve o endereco. A pagina faz a
 * chamada, preenche o formulario e trata a resposta.
 */
@Service
public class CepService {

	private static final Logger LOG = LoggerFactory.getLogger(CepService.class);

	private final RestClient restClient;

	public CepService(RestClient brasilApiRestClient) {
		this.restClient = brasilApiRestClient;
	}

	public Optional<Endereco> buscar(String cep) {
		String limpo = cep == null ? "" : cep.replaceAll("\\D", "");
		if (limpo.length() != 8) {
			throw new IllegalArgumentException(
					"CEP inválido: " + cep + ". O formato esperado é 8 dígitos (ex.: 59650000).");
		}
		try {
			Resposta resposta = restClient.get()
					.uri("/api/cep/v2/{cep}", limpo)
					.retrieve()
					.body(Resposta.class);
			if (resposta == null) {
				return Optional.empty();
			}
			Endereco endereco = new Endereco();
			endereco.setCep(resposta.cep());
			endereco.setRua(resposta.street());
			endereco.setBairro(resposta.neighborhood());
			endereco.setCidade(resposta.city());
			return Optional.of(endereco);
		} catch (RestClientException excecao) {
			LOG.warn("Falha ao consultar o CEP {}: {}", limpo, excecao.getMessage());
			return Optional.empty();
		}
	}

	/** Recorte da resposta da BrasilAPI. */
	public record Resposta(String cep, String state, String city, String neighborhood, String street) {
	}

}

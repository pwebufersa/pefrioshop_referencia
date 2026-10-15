package br.edu.ufersa.pefrioshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Cliente HTTP usado na consulta de CEP (US 10).
 *
 * <p>A biblioteca de HTTP fica envolvida em uma interface propria do projeto:
 * o servico recebe o {@link RestClient} pronto, e nenhum outro ponto do codigo
 * conhece a URL da BrasilAPI.
 */
@Configuration
public class ClienteHttpConfig {

	@Bean
	public RestClient brasilApiRestClient() {
		return RestClient.builder()
				.baseUrl("https://brasilapi.com.br")
				.build();
	}

}

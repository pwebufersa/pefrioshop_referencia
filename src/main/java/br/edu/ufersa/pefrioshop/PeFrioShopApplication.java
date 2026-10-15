package br.edu.ufersa.pefrioshop;

import br.edu.ufersa.pefrioshop.config.ParametrosLoja;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ParametrosLoja.class)
public class PeFrioShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeFrioShopApplication.class, args);
	}

}

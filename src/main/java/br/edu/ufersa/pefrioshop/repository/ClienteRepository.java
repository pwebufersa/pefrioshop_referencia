package br.edu.ufersa.pefrioshop.repository;

import br.edu.ufersa.pefrioshop.domain.Cliente;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findAllByOrderByNomeCompletoAsc();

	/** Aniversariantes do mes, independente do ano (US 11). */
	List<Cliente> findByDataNascimentoMonth(int mes);

	/** Clientes cadastrados em um intervalo (US 16). */
	List<Cliente> findByDataCadastroBetween(LocalDateTime inicio, LocalDateTime fim);

}

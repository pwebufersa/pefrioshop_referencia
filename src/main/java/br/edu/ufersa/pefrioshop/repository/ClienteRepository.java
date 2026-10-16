package br.edu.ufersa.pefrioshop.repository;

import br.edu.ufersa.pefrioshop.domain.Cliente;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findAllByOrderByNomeCompletoAsc();

	/**
	 * Aniversariantes do mês, independente do ano (US 11).
	 *
	 * <p>A extração do mês é feita em JPQL porque a consulta derivada com a
	 * palavra-chave Month não é resolvida pelo Hibernate desta versão.
	 */
	@Query("select c from Cliente c where extract(month from c.dataNascimento) = :mes order by c.nomeCompleto")
	List<Cliente> aniversariantesDoMes(@Param("mes") int mes);

	/** Clientes cadastrados em um intervalo (US 16). */
	List<Cliente> findByDataCadastroBetween(LocalDateTime inicio, LocalDateTime fim);

}

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
	 * <p>A consulta é declarada porque o nome do método não expressa extração de
	 * mês. O Spring Data lê o nome como caminho de propriedade, e o sufixo Month
	 * passa a ser a propriedade month de dataNascimento, o que produz uma junção
	 * e falha. Das doze consultas do projeto, dez são derivadas do nome, e apenas
	 * esta e a contagem por tipo são declaradas, por exigirem função de data e
	 * agregação, que o analisador de nomes não cobre.
	 */
	@Query("select c from Cliente c where extract(month from c.dataNascimento) = :mes order by c.nomeCompleto")
	List<Cliente> aniversariantesDoMes(@Param("mes") int mes);

	/** Clientes cadastrados em um intervalo (US 16). */
	List<Cliente> findByDataCadastroBetween(LocalDateTime inicio, LocalDateTime fim);

}

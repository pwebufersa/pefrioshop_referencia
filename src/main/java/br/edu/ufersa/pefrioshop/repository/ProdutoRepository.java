package br.edu.ufersa.pefrioshop.repository;

import br.edu.ufersa.pefrioshop.domain.Produto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

	/** Busca pelo nome (US 6). */
	List<Produto> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

	/** Os quatro produtos mais novos (US 14). */
	List<Produto> findTop4ByOrderByDataCadastroDescIdDesc();

	/** Todos, do menor para o maior numero BR (US 13). */
	List<Produto> findAllByOrderByNumeroBrAsc();

	/** Contagem de produtos por tipo (US 15). */
	@Query("select p.tipo as tipo, count(p) as quantidade from Produto p group by p.tipo order by p.tipo")
	List<ContagemPorTipo> contarPorTipo();

}

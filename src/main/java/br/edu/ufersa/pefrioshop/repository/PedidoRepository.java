package br.edu.ufersa.pefrioshop.repository;

import br.edu.ufersa.pefrioshop.domain.Pedido;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	/** Pedidos de um intervalo (US 5). */
	List<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim);

	List<Pedido> findAllByOrderByDataPedidoDesc();

	/** Ultimo pedido de um cliente (US 9). */
	Optional<Pedido> findFirstByClienteIdOrderByDataPedidoDesc(Long clienteId);

}

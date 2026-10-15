package br.edu.ufersa.pefrioshop.service;

import br.edu.ufersa.pefrioshop.domain.Cliente;
import br.edu.ufersa.pefrioshop.repository.ClienteRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

	private final ClienteRepository clientes;

	public ClienteService(ClienteRepository clientes) {
		this.clientes = clientes;
	}

	public List<Cliente> listar() {
		return clientes.findAllByOrderByNomeCompletoAsc();
	}

	public Cliente buscar(Long id) {
		return clientes.findById(id).orElseThrow(() -> new IllegalArgumentException(mensagemNaoEncontrado(id)));
	}

	public Cliente salvar(Cliente cliente) {
		return clientes.save(cliente);
	}

	@Transactional
	public void excluir(Long id) {
		Cliente cliente = buscar(id);
		try {
			clientes.delete(cliente);
			clientes.flush();
		} catch (DataIntegrityViolationException excecao) {
			throw new IllegalArgumentException("O cliente " + cliente.getNomeCompleto() + " (id=" + id
					+ ") possui pedidos e não pode ser excluído. Exclua os pedidos primeiro.");
		}
	}

	/** Aniversariantes do mes informado (US 11). */
	public List<Cliente> aniversariantes(int mes) {
		validarMes(mes);
		return clientes.findByDataNascimentoMonth(mes);
	}

	/** Clientes cadastrados no mes e ano informados (US 16). */
	public List<Cliente> novatos(int mes, int ano) {
		validarMes(mes);
		YearMonth periodo = YearMonth.of(ano, mes);
		LocalDateTime inicio = periodo.atDay(1).atStartOfDay();
		LocalDateTime fim = periodo.atEndOfMonth().atTime(LocalTime.MAX);
		return clientes.findByDataCadastroBetween(inicio, fim);
	}

	private void validarMes(int mes) {
		if (mes < 1 || mes > 12) {
			throw new IllegalArgumentException("Mês inválido: " + mes + ". O formato esperado é de 1 a 12.");
		}
	}

	private String mensagemNaoEncontrado(Long id) {
		return "Cliente não encontrado: id=" + id + ". Verifique a lista de clientes.";
	}

}
